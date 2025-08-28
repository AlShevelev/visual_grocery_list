package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto.Popup
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto.ScreenEvent
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto.ScreenState
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.Constants
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.architecture.FlagsStorage
import com.shevelev.visualgrocerylist.storage.database.repository.DatabaseRepository
import com.shevelev.visualgrocerylist.storage.file.FileRepository
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

internal class EditItemsViewModel(
    private val databaseRepository: DatabaseRepository,
    private val fileRepository: FileRepository,
    private val flags: FlagsStorage,
) : ViewModel(), UserActionsHandler {
    var searchQuery by mutableStateOf("")
        private set

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState(loading = true))
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private val _screenEvent = MutableSharedFlow< ScreenEvent>()
    val screenEvent: SharedFlow<ScreenEvent> = _screenEvent.asSharedFlow()

    private var _searchJob: Job? = null

    private var itemsWereEdited = false

    init {
        viewModelScope.launch {
            val allDbItems = databaseRepository.getAllGroceryItems()
            val screenItems = allDbItems.map {
                GridItem(
                    id = it.id.toString(),
                    dbId = it.id,
                    title = it.keyWord,
                    imageFile = fileRepository.getFileByName(it.imageFile),
                )
            }

            updateState { it.copy(loading = false, items = screenItems) }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery

        _searchJob?.cancel()
        _searchJob = viewModelScope.launch {
            val result = if (newQuery.isEmpty()) {
                emptyList<GridItem>()
            } else {
                delay(Constants.SEARCH_DEBOUNCE_PAUSE_MILLIS.milliseconds)

                databaseRepository
                    .getGroceryItemByKeyWord(searchQuery)
                    .map {
                        GridItem(
                            id = it.id.toString(),
                            imageFile = fileRepository.getFileByName(it.imageFile),
                            dbId = it.id,
                            title = it.keyWord,
                        )
                    }
            }

            _screenState.emit(ScreenState(items = result))
        }
    }

    override fun onDeleteItemClick(item: GridItem) {
        viewModelScope.launch {
            updateState { it.copy(popup = Popup.DeleteConfirmationPopup(item)) }
        }
    }

    override fun onDeleteItemConfirmed(dbId: Long) {
        viewModelScope.launch {
            databaseRepository.removeGroceryListItemByGroceryItemId(dbId)

            databaseRepository.getGroceryItemById(dbId)?.let {
                databaseRepository.removeGroceryItem(it)
            }

            val items = _screenState.value.items.toMutableList()
            items.removeIf { it.dbId == dbId }

            itemsWereEdited = true

            updateState { it.copy(popup = null, items = items) }
        }
    }

    override fun onDeleteItemRejected() {
        viewModelScope.launch {
            updateState { it.copy(popup = null) }
        }
    }

    override fun onEditNameClick(item: GridItem) {
        viewModelScope.launch {
            updateState {
                it.copy(popup = Popup.NamePopup(
                    name = item.title,
                    item = item,
                ))
            }
        }
    }

    override fun onEditNameConfirmed(
        newName: String,
        item: GridItem,
    ) {
        viewModelScope.launch {
            val dbItem = databaseRepository.getGroceryItemById(item.dbId) ?: return@launch

            databaseRepository.updateGroceryItem(dbItem.copy(keyWord = newName))

            val items = _screenState.value.items.toMutableList()

            items
                .indexOfFirst { it.dbId == item.dbId }
                .takeIf { it != -1 }
                ?.also { index ->
                    items[index] = item.copy(title = newName)
                }

            itemsWereEdited = true

            updateState { it.copy(items = items, popup = null) }
        }
    }

    override fun onEditNameRejected() {
        viewModelScope.launch {
            updateState { it.copy(popup = null) }
        }
    }

    private suspend fun updateState(updateAction: (ScreenState) -> ScreenState) {
        _screenState.emit(updateAction(_screenState.value))
    }
}