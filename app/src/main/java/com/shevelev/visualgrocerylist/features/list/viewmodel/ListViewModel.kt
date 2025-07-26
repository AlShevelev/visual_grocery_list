package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.ScreenState
import com.shevelev.visualgrocerylist.storage.file.FileRepository
import com.shevelev.visualgrocerylist.storage.database.repository.DatabaseRepository
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class ListViewModel(
    private val databaseRepository: DatabaseRepository,
    private val fileRepository: FileRepository,
) : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            val allItems = databaseRepository.getAllGroceryListItemCombined().map {
                @Suppress("DEPRECATION")
                GridItem(
                    id = it.listItem.id.toString(),
                    dbId = it.listItem.id,
                    imageFile = fileRepository.getFileByName(it.groceryItem.imageFile),
                    title = it.groceryItem.keyWord.capitalize(Locale.getDefault()),
                    checked = it.listItem.checked,
                )
            }
            _screenState.emit(ScreenState.Data(allItems))
        }
    }
}