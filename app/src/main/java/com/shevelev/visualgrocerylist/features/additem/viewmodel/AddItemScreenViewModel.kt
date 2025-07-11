package com.shevelev.visualgrocerylist.features.additem.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.GridItem
import com.shevelev.visualgrocerylist.database.repository.DatabaseRepository
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class AddItemScreenViewModel(
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    @OptIn(FlowPreview::class)
    val searchResults: StateFlow<List<GridItem>> =
        snapshotFlow { searchQuery }
            .debounce(SEARCH_DEBOUNCE_PAUSE.milliseconds)
            .map { searchQuery ->
                if (searchQuery.isNotEmpty()) {
                    databaseRepository
                        .findGroceryItemByKeyWord(searchQuery)
                        .map { GridItem.Db(it) }
                } else {
                    emptyList()
                } + GridItem.SearchInternet
            }.stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.Companion.WhileSubscribed(5_000)
            )

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    companion object {
        private const val SEARCH_DEBOUNCE_PAUSE = 500L
    }
}