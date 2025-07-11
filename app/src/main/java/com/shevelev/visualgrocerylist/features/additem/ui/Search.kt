package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.GridTile
import com.shevelev.visualgrocerylist.database.entities.GroceryItem
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchContent(
    searchQuery: String,
    searchResults: List<GridItem>,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current

    val dimensions = LocalDimensions.current

    DockedSearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = { keyboardController?.hide() },
        modifier = modifier.fillMaxSize(),
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
        placeholder = {
            Text(text = context.getString(R.string.search_here))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        content = {
            if (searchResults.isEmpty()) {
                EmptySearchResult()
            } else {
                LazyVerticalGrid(
                    contentPadding = PaddingValues(dimensions.paddingHalf),

                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = searchResults.size,
                        key = { index -> searchResults[index].id },
                        itemContent = { index ->
                            when (val item = searchResults[index]) {
                                is GridItem.Db -> DbItemTile(
                                    item = item.item,
                                )
                                is GridItem.SearchInternet -> SearchTheInternetTile()
                            }
                        }
                    )
                }
            }
        },
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp
    )
}

@Composable
private fun EmptySearchResult(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = context.getString(R.string.nothing_found),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = context.getString(R.string.try_adjusting_your_search),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun DbItemTile(
    item: GroceryItem,
    modifier: Modifier = Modifier,
) {
    return GridTile(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun SearchTheInternetTile(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val dimensions = LocalDimensions.current

    return GridTile(
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.outline_public_24),
                contentDescription = null,
                modifier = Modifier.scale(1f).size(40.dp)
            )
            Spacer(modifier = Modifier.height(dimensions.paddingSingleAndHalf))
            Text(
                text = context.getString(R.string.search_the_internet),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
