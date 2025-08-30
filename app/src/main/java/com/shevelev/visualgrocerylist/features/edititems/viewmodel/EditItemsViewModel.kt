package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.viewmodel

import android.graphics.Bitmap
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

    private var selectedItem: GridItem? = null

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
        selectedItem = item

        viewModelScope.launch {
            updateState { it.copy(popup = Popup.DeleteConfirmationPopup(item)) }
        }
    }

    override fun onDeleteItemConfirmed() {
        viewModelScope.launch {
            val item = selectedItem ?: return@launch

            databaseRepository.removeGroceryListItemByGroceryItemId(item.dbId)

            databaseRepository.getGroceryItemById(item.dbId)?.let {
                databaseRepository.removeGroceryItem(it)
            }

            val items = _screenState.value.items.toMutableList()
            items.removeIf { it.dbId == item.dbId }

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
        selectedItem = item

        viewModelScope.launch {
            updateState {
                it.copy(popup = Popup.NamePopup(
                    name = item.title,
                    item = item,
                ))
            }
        }
    }

    override fun onEditNameConfirmed(newName: String) {
        viewModelScope.launch {
            val item = selectedItem ?: return@launch

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

    override fun onEditImageClick(item: GridItem) {
        selectedItem = item

        viewModelScope.launch {
            updateState {
                it.copy(popup = Popup.ImageSelectionPopup(item = item))
            }
        }
    }

    override fun onEditImageSearchSelected() {
        // remove the old file
        TODO("Not yet implemented")
    }

    override fun onEditImageDismissed() {
        viewModelScope.launch {
            updateState { it.copy(popup = null) }
        }
    }

    override fun onBitmapCaptured(bitmap: Bitmap) {
        viewModelScope.launch {
            val item = selectedItem ?: return@launch

            val dbItem = databaseRepository.getGroceryItemById(item.dbId) ?: return@launch

            val itemIndex = _screenState.value.items
                .indexOfFirst { it.id == item.id }
                .takeIf { it != -1 }
                ?: return@launch

            fileRepository.delete(item.imageFile)
            val saveResult = fileRepository.save(bitmap)

            saveResult.onSuccess { fileName ->
                databaseRepository.updateGroceryItem(dbItem.copy(imageFile = fileName))

                val imageFile = fileRepository.getFileByName(fileName)

                val items = _screenState.value.items.toMutableList()
                items[itemIndex] = item.copy(imageFile = imageFile)

                updateState { it.copy(items = items) }

                itemsWereEdited = true
            }.onFailure {
                _screenEvent.emit(ScreenEvent.Error)
            }
        }
        // update the item and the screen state
    }

    private suspend fun updateState(updateAction: (ScreenState) -> ScreenState) {
        _screenState.emit(updateAction(_screenState.value))
    }
}