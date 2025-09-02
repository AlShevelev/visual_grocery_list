package com.shevelev.visualgrocerylist.features.searchimage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.visualgrocerylist.features.searchimage.dto.GridItem
import com.shevelev.visualgrocerylist.features.searchimage.dto.ScreenEvent
import com.shevelev.visualgrocerylist.features.searchimage.dto.ScreenState
import com.shevelev.visualgrocerylist.shared.architecture.FlagsStorage
import com.shevelev.visualgrocerylist.network.repository.SearchRepository
import com.shevelev.visualgrocerylist.shared.architecture.Flag
import com.shevelev.visualgrocerylist.storage.file.FileRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class SearchImageViewModel(
    private val searchRepository: SearchRepository,
    private val flags: FlagsStorage,
    private val fileRepository: FileRepository,
) : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState>(
        ScreenState(items =  emptyList(), loading = true)
    )
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private val _screenEvent = MutableSharedFlow< ScreenEvent>()
    val screenEvent: SharedFlow<ScreenEvent> = _screenEvent.asSharedFlow()

    fun init(keyWord: String) {
        viewModelScope.launch {
            setLoading(true)

            val searchResult = searchRepository.search(keyWord)

            searchResult.onSuccess { result ->
                val itemsToAdd = result.images.map {
                    GridItem(
                        id = it.id,
                        imageLink = it.thumbnailLink,
                    )
                }

                _screenState.emit(
                    ScreenState(
                        items = itemsToAdd,
                        loading = false,
                        keyWord = keyWord,
                    )
                )
            }.onFailure {
                _screenEvent.emit(ScreenEvent.Error)
                setLoading(false)
            }
        }
    }

    fun onGridItemClick(item: GridItem) {
        viewModelScope.launch {
            setLoading(true)
            val result = fileRepository.download(item.imageLink)
            setLoading(false)

            result.onSuccess { fileName ->
                flags.setFlag(Flag.SearchedImage(fileName))
                _screenEvent.emit(ScreenEvent.Close)
            }.onFailure {
                _screenEvent.emit(ScreenEvent.Error)
            }
        }
    }

    private suspend fun setLoading(loading: Boolean) {
        _screenState.emit(
            _screenState.value.copy(loading = loading)
        )
    }
}