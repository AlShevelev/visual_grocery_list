package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.LastDeletedItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.NotePopup
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.ScreenEvent
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.ScreenState
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.architecture.Flags
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.architecture.FlagsStorage
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItemCombined
import com.shevelev.visualgrocerylist.storage.file.FileRepository
import com.shevelev.visualgrocerylist.storage.database.repository.DatabaseRepository
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ListViewModel(
    private val databaseRepository: DatabaseRepository,
    private val fileRepository: FileRepository,
    private val flags: FlagsStorage,
) : ViewModel(), UserActionsHandler {
    private val gridItems = mutableListOf<GroceryListItemCombined>()

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private val _screenEvent = MutableSharedFlow< ScreenEvent>()
    val screenEvent: SharedFlow<ScreenEvent> = _screenEvent.asSharedFlow()

    private var lastDeletedItem: LastDeletedItem? = null

    init {
        refresh(needRefresh = true)
    }

    fun tryToRefresh() = refresh(flags.hasFlag(Flags.MUST_REFRESH_LIST))

    override fun onCheckedChange(dbId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val gridItemIndex = getItemIndexByDbId(dbId)

                val gridItem = gridItems[gridItemIndex]
                val listItem = gridItem.listItem.copy(checked = !gridItem.listItem.checked)
                gridItems[gridItemIndex] = gridItem.copy(listItem = listItem)

                databaseRepository.updateGroceryListItem(listItem)

                _screenState.emit(ScreenState.Data(gridItems.map { it.mapToView() }))
            }
        }
    }

    override fun onDeleteItemClick(dbId: Long) {
        viewModelScope.launch {
            val gridItemIndex = getItemIndexByDbId(dbId)

            val gridItem = gridItems[gridItemIndex]
            databaseRepository.removeGroceryListItem(gridItem.listItem)
            gridItems.removeAt(gridItemIndex)

            lastDeletedItem = LastDeletedItem(gridItem, gridItemIndex)

            _screenState.emit(ScreenState.Data(gridItems.map { it.mapToView() }))

            val gridItemView = gridItem.mapToView()
            _screenEvent.emit(
                ScreenEvent.ShowDeleteNotification(gridItemView.title, gridItemView.dbId)
            )
        }
    }

    override fun onRestoreDeletedItemClick(dbId: Long) {
        viewModelScope.launch {
            lastDeletedItem?.let {
                databaseRepository.addGroceryListItem(it.item.listItem)

                gridItems.add(it.index, it.item)

                _screenState.emit(ScreenState.Data(gridItems.map { it.mapToView() }))
            }
        }
    }

    override fun onNoteClick(dbId: Long) {
        val state = _screenState.value as? ScreenState.Data ?: return
        val gridListItem = gridItems[getItemIndexByDbId(dbId)].listItem
        val gridGroceryItem = gridItems[getItemIndexByDbId(dbId)].groceryItem

        viewModelScope.launch {
            _screenState.emit(
                state.copy(notePopup = NotePopup(
                    dbId = gridListItem.id,
                    note = gridListItem.note,
                    title = gridGroceryItem.keyWord,
                ))
            )
        }
    }

    override fun onUpdateNote(dbId: Long, note: String) {
        val state = _screenState.value as? ScreenState.Data ?: return

        val gridItemIndex = getItemIndexByDbId(dbId)
        val gridItem = gridItems[gridItemIndex]
        val listItem = gridItem.listItem

        viewModelScope.launch {
            if (listItem.note != note) {
                val newListItem = listItem.copy(note = note)
                databaseRepository.updateGroceryListItem(newListItem)

                gridItems[gridItemIndex] = gridItem.copy(listItem = newListItem)
            }

            _screenState.emit(
                state.copy(notePopup = null, items = gridItems.map { it.mapToView() })
            )
        }
    }

    override fun onNotePopupDismissed() {
        val state = _screenState.value as? ScreenState.Data ?: return

        viewModelScope.launch {
            _screenState.emit(state.copy(notePopup = null))
        }
    }

    override fun onReorderItem(fromIndex: Int, toIndex: Int) {
        if (fromIndex == toIndex) {
            return
        }

        val state = _screenState.value as? ScreenState.Data ?: return

        viewModelScope.launch {
            val toOrder = gridItems[toIndex].listItem.order
            val toListItem = gridItems[toIndex].listItem.copy(order = gridItems[fromIndex].listItem.order)
            val fromListItem = gridItems[fromIndex].listItem.copy(order = toOrder)

            databaseRepository.updateGroceryListItem(toListItem)
            databaseRepository.updateGroceryListItem(fromListItem)

            gridItems[fromIndex] = gridItems[fromIndex].copy(listItem = fromListItem)
            gridItems[toIndex] = gridItems[toIndex].copy(listItem = toListItem)

            val gridItem = gridItems[fromIndex]
            gridItems[fromIndex] = gridItems[toIndex]
            gridItems[toIndex] = gridItem

            _screenState.emit(state.copy(items = gridItems.map { it.mapToView() }))
        }
    }

    override fun onListClearConfirmationStarted() {
        val state = _screenState.value as? ScreenState.Data ?: return

        viewModelScope.launch {
            _screenState.emit(state.copy(clearListConfirmationDialogIsShown = true))
        }
    }

    override fun onListClearConfirmed() {
        val state = _screenState.value as? ScreenState.Data ?: return

        val itemsToClear = gridItems.map { it.listItem }
        gridItems.clear()

        viewModelScope.launch {
            for (item in itemsToClear) {
                databaseRepository.removeGroceryListItem(item)
            }

            _screenState.emit(
                state.copy(items = emptyList(), clearListConfirmationDialogIsShown = false)
            )
        }
    }

    override fun onListClearRejected() {
        val state = _screenState.value as? ScreenState.Data ?: return

        viewModelScope.launch {
            _screenState.emit(state.copy(clearListConfirmationDialogIsShown = false))
        }
    }

    private fun refresh(needRefresh: Boolean) {
        if (!needRefresh) {
            return
        }

        viewModelScope.launch {
            val sourceDbItems = databaseRepository.getAllGroceryListItemCombined()

            val allItems = sourceDbItems.map { it.mapToView() }

            gridItems.clear()
            gridItems.addAll(sourceDbItems)

            _screenState.emit(ScreenState.Data(allItems))
        }
    }

    @Suppress("DEPRECATION")
    private fun GroceryListItemCombined.mapToView() = GridItem(
        id = listItem.id.toString(),
        dbId = listItem.id,
        imageFile = fileRepository.getFileByName(groceryItem.imageFile),
        title = groceryItem.keyWord.capitalize(Locale.getDefault()),
        checked = listItem.checked,
        hasNote = listItem.note?.isNotEmpty() == true
    )

    private fun getItemIndexByDbId(dbId: Long): Int {
        val gridItemIndex = gridItems.indexOfFirst { it.listItem.id == dbId }
        if (gridItemIndex == -1) {
            throw IllegalArgumentException("Item with dbId [$dbId] is not found")
        }

        return gridItemIndex
    }
}