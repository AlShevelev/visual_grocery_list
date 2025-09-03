package com.shevelev.visualgrocerylist.features.additem.viewmodel

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.GridRow
import com.shevelev.visualgrocerylist.features.additem.dto.GridItem
import com.shevelev.visualgrocerylist.features.additem.dto.NamePopup
import com.shevelev.visualgrocerylist.features.additem.dto.ScreenEvent
import com.shevelev.visualgrocerylist.features.additem.dto.ScreenState
import com.shevelev.visualgrocerylist.shared.Constants
import com.shevelev.visualgrocerylist.shared.architecture.FlagsStorage
import com.shevelev.visualgrocerylist.storage.file.FileRepository
import com.shevelev.visualgrocerylist.storage.database.repository.DatabaseRepository
import com.shevelev.visualgrocerylist.network.repository.SearchRepository
import com.shevelev.visualgrocerylist.shared.architecture.Flag
import kotlin.String
import kotlin.time.Duration.Companion.milliseconds
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class AddItemScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    private val searchRepository: SearchRepository,
    private val fileRepository: FileRepository,
    private val flags: FlagsStorage,
) : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState(items =  emptyList()))
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private val _screenEvent = MutableSharedFlow< ScreenEvent>()
    val screenEvent: SharedFlow<ScreenEvent> = _screenEvent.asSharedFlow()

    private var _searchJob: Job? = null

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery

        _searchJob?.cancel()
        _searchJob = viewModelScope.launch {
            val result = if (newQuery.isEmpty()) {
                emptyList<GridItem>()
            } else {
                delay(Constants.SEARCH_DEBOUNCE_PAUSE_MILLIS.milliseconds)

                val dbItems = databaseRepository
                    .getGroceryItemByKeyWord(searchQuery)
                    .map {
                        GridItem.Db(
                            id = it.id.toString(),
                            imageFile = fileRepository.getFileByName(it.imageFile),
                            dbId = it.id,
                            title = it.keyWord,
                        )
                    }

                listOf(
                    GridItem.SearchInternetAction(enabled = true),
                    GridItem.GalleryAction,
                    GridItem.MakePhotoAction
                ) + dbItems
            }

            _screenState.emit(ScreenState(items = result.toRows()))
        }
    }

    fun onSearchTheInternetClick() {
        _searchJob?.cancel()
        _searchJob = viewModelScope.launch {
            setLoading(true)

            val searchResult = searchRepository.search(searchQuery)

            val items = mutableListOf<GridItem>(
                GridItem.SearchInternetAction(enabled = false),
                GridItem.GalleryAction,
                GridItem.MakePhotoAction
            )

            searchResult.onSuccess { result ->
                val itemsToAdd = result.images.map {
                    GridItem.Internet(
                        id = it.id,
                        imageLink = it.thumbnailLink,
                    )
                }

                items.addAll(itemsToAdd)

                _screenState.emit(ScreenState(items = items.toRows(), loading = false))
            }.onFailure {
                _screenEvent.emit(ScreenEvent.Error)
                setLoading(false)
            }
        }
    }

    fun onInternetItemClick(item: GridItem.Internet) {
        viewModelScope.launch {
            _screenState.emit(
                _screenState.value.copy(namePopup = NamePopup(name = searchQuery, item = item))
            )
        }
    }

    fun onCapturedItemClick(item: GridItem.Captured) {
        viewModelScope.launch {
            _screenState.emit(
                _screenState.value.copy(namePopup = NamePopup(name = searchQuery, item = item))
            )
        }
    }

    fun onNameConfirmed(item: GridItem, name: String) {
        viewModelScope.launch {
            _screenState.emit(_screenState.value.copy(namePopup = null))
            setLoading(true)

            val result = when (item) {
                is GridItem.Internet -> fileRepository.download(item.imageLink)
                is GridItem.Captured -> fileRepository.save(item.bitmap)
                else -> Result.failure(IllegalArgumentException())
            }

            result.onSuccess { fileName ->
                val itemDbId = databaseRepository.addGroceryItem(name, fileName)
                databaseRepository.addGroceryListItemToTop(groceryItemDbId = itemDbId)

                closeScreen()
            }.onFailure {
                _screenEvent.emit(ScreenEvent.Error)
                setLoading(false)
            }
        }
    }

    fun onNameRejected() {
        viewModelScope.launch {
            _screenState.emit(_screenState.value.copy(namePopup = null))
        }
    }

    fun onDbItemClick(item: GridItem.Db) {
        viewModelScope.launch {
            val listItem = databaseRepository.getGroceryListItemByGroceryItemId(item.dbId)

            if (listItem != null) {
                databaseRepository.moveGroceryListItemToTop(listItem.copy(checked = false))
            } else {
                databaseRepository.addGroceryListItemToTop(item.dbId)
            }

            closeScreen()
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onBitmapCaptured(bitmap: Bitmap) {
        viewModelScope.launch {
            val itemToInsert = GridItem.Captured(
                id = Uuid.random().toString(),
                bitmap = bitmap,
            )

            val items = mutableListOf<GridItem>(
                GridItem.SearchInternetAction(enabled = true),
                GridItem.GalleryAction,
                GridItem.MakePhotoAction,
                itemToInsert
            )

            _screenState.emit(_screenState.value.copy(items = items.toRows()))
        }
    }

    private suspend fun setLoading(loading: Boolean) {
        _screenState.emit(
            _screenState.value.copy(loading = loading)
        )
    }

    private suspend fun closeScreen() {
        flags.setFlag(Flag.MustRefreshList)
        _screenEvent.emit(ScreenEvent.Close)
    }

    private fun List<GridItem>.toRows(): List<GridRow> {
        if (isEmpty()) {
            return emptyList()
        }

        val items = this

        return buildList {
            add(GridRow(items = listOf(items[0], items[1], items[2])))

            for (i in 3..items.lastIndex step 2) {
                add(
                    GridRow(
                        items = buildList {
                            add(items[i])

                            if (i + 1 > items.lastIndex) {
                                add(GridItem.Empty)
                            } else {
                                add(items[i + 1])
                            }
                        }
                    )
                )
            }
        }
    }
}