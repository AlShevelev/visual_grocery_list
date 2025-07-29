package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.LastDeletedItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.ScreenEvent
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.ScreenState
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
) : ViewModel(), UserActionsHandler {
    private val dbItems = mutableListOf<GroceryListItemCombined>()

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private val _screenEvent = MutableSharedFlow< ScreenEvent>()
    val screenEvent: SharedFlow<ScreenEvent> = _screenEvent.asSharedFlow()

    private var lastDeletedItem: LastDeletedItem? = null

    init {
        viewModelScope.launch {
            val sourceDbItems = databaseRepository.getAllGroceryListItemCombined()

            val allItems = sourceDbItems.map { it.mapToView() }

            dbItems.addAll(sourceDbItems)

            _screenState.emit(ScreenState.Data(allItems))
        }
    }

    override fun onCheckedChange(dbId: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val dbItemIndex = getItemIndexByDbId(dbId)

                val dbItem = dbItems[dbItemIndex]
                val listItem = dbItem.listItem.copy(checked = !dbItem.listItem.checked)
                dbItems[dbItemIndex] = dbItem.copy(listItem = listItem)

                databaseRepository.updateGroceryListItem(listItem)

                _screenState.emit(ScreenState.Data(dbItems.map { it.mapToView() }))
            }
        }
    }

    override fun onDeleteItemClick(dbId: Long) {
        viewModelScope.launch {
            val dbItemIndex = getItemIndexByDbId(dbId)

            val dbItem = dbItems[dbItemIndex]
            databaseRepository.removeGroceryListItem(dbItem.listItem)
            dbItems.removeAt(dbItemIndex)

            lastDeletedItem = LastDeletedItem(dbItem, dbItemIndex)

            _screenState.emit(ScreenState.Data(dbItems.map { it.mapToView() }))

            val dbItemView = dbItem.mapToView()
            _screenEvent.emit(
                ScreenEvent.ShowDeleteNotification(dbItemView.title, dbItemView.dbId)
            )
        }
    }

    override fun onRestoreDeletedItemClick(dbId: Long) {
        viewModelScope.launch {
            lastDeletedItem?.let {
                databaseRepository.addGroceryListItem(it.item.listItem)

                dbItems.add(it.index, it.item)


                _screenState.emit(ScreenState.Data(dbItems.map { it.mapToView() }))
            }
        }
    }

    private fun GroceryListItemCombined.mapToView() = GridItem(
        id = listItem.id.toString(),
        dbId = listItem.id,
        imageFile = fileRepository.getFileByName(groceryItem.imageFile),
        title = groceryItem.keyWord.capitalize(Locale.getDefault()),
        checked = listItem.checked,
    )

    private fun getItemIndexByDbId(dbId: Long): Int {
        val dbItemIndex = dbItems.indexOfFirst { it.listItem.id == dbId }
        if (dbItemIndex == -1) {
            throw IllegalArgumentException("Item with dbId [$dbId] is not found")
        }

        return dbItemIndex
    }
}