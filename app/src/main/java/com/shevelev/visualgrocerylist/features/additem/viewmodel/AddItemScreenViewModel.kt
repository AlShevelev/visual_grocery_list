package com.shevelev.visualgrocerylist.features.additem.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.ScreenEvent
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.ScreenState
import com.shevelev.visualgrocerylist.storage.file.FileRepository
import com.shevelev.visualgrocerylist.storage.database.repository.DatabaseRepository
import com.shevelev.visualgrocerylist.network.repository.SearchRepository
import kotlin.String
import kotlin.time.Duration.Companion.milliseconds
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
) : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState())
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private val _screenEvent = MutableSharedFlow< ScreenEvent>()
    val screenEvent: SharedFlow<ScreenEvent> = _screenEvent.asSharedFlow()

    private var dbItems: List<GridItem> = emptyList()

    private var _searchJob: Job? = null

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery

        _searchJob?.cancel()
        _searchJob = viewModelScope.launch {
            val result = if (newQuery.isEmpty()) {
                emptyList()
            } else {
                delay(SEARCH_DEBOUNCE_PAUSE.milliseconds)

                dbItems = databaseRepository
                    .findGroceryItemByKeyWord(searchQuery)
                    .map {
                        GridItem.Db(
                            id = it.id.toString(),
                            imageFile = fileRepository.getFileByName(it.imageFile),
                            dbId = it.id,
                        )
                    }

                dbItems + GridItem.SearchInternet
            }


            _screenState.emit(ScreenState(items = result))
        }
    }

    fun onSearchTheInternetClick() {
        _searchJob?.cancel()
        _searchJob = viewModelScope.launch {
            setLoading(true)

            val searchResult = searchRepository.search(searchQuery)

            searchResult.onSuccess { result ->
                _screenState.emit(
                    ScreenState(
                        items = dbItems + result.images.map {
                            GridItem.Internet(
                                id = it.id,
                                imageLink = it.thumbnailLink,
                            )
                        },
                        loading = false,
                    )
                )
            }.onFailure {
                _screenEvent.emit(ScreenEvent.Error)
                setLoading(false)
            }
        }
    }

    fun onInternetItemClick(item: GridItem.Internet) {
        viewModelScope.launch {
            setLoading(true)

            fileRepository.download(item.imageLink).onSuccess { fileName ->
                val itemDbId = databaseRepository.addGroceryItem(searchQuery, fileName)
                databaseRepository.addGroceryListItemToTop(groceryItemDbId = itemDbId)

                _screenEvent.emit(ScreenEvent.Close)
            }.onFailure {
                _screenEvent.emit(ScreenEvent.Error)
                setLoading(false)
            }
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

            _screenEvent.emit(ScreenEvent.Close)
        }
    }

    private suspend fun setLoading(loading: Boolean) {
        _screenState.emit(
            _screenState.value.copy(loading = loading)
        )
    }

    companion object {
        private const val SEARCH_DEBOUNCE_PAUSE = 500L
    }
}