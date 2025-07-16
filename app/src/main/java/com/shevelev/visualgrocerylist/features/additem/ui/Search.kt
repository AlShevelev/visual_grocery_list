package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.ScreenState
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.GridTile
import com.shevelev.visualgrocerylist.database.entities.GroceryItem
import com.shevelev.visualgrocerylist.network.dto.ImageDto
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchContent(
    searchQuery: String,
    screenState: ScreenState,
    onSearchQueryChange: (String) -> Unit,
    onSearchTheInternetClick: () -> Unit,
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
        enabled = !screenState.loading,
        placeholder = {
            Text(text = context.getString(R.string.search_here))
        },
        leadingIcon = {
            if (screenState.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(24.dp).padding(top = 14.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = { onSearchQueryChange("") },
                    enabled = !screenState.loading,
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        content = {
            if (screenState.items.isEmpty()) {
                EmptySearchResult()
            } else {
                LazyVerticalGrid(
                    contentPadding = PaddingValues(dimensions.paddingHalf),

                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = screenState.items.size,
                        key = { index -> screenState.items[index].id },
                        itemContent = { index ->
                            when (val item = screenState.items[index]) {
                                is GridItem.Db -> DbItemTile(
                                    item = item.item,
                                    enabled = !screenState.loading,
                                )
                                is GridItem.SearchInternet -> SearchTheInternetTile(
                                    onSearchTheInternetClick = onSearchTheInternetClick,
                                    enabled = !screenState.loading,
                                )
                                is GridItem.Internet -> InternetItemTile(
                                    item.item,
                                    enabled = !screenState.loading,
                                )
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
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    return GridTile(
        modifier = modifier,
        enabled = enabled,
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
    onSearchTheInternetClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val dimensions = LocalDimensions.current

    val keyboardController = LocalSoftwareKeyboardController.current

    return GridTile(
        modifier = modifier,
        enabled = enabled,
        onClick = {
            keyboardController?.hide()
            onSearchTheInternetClick ()
        },
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

@Composable
private fun InternetItemTile(
    item: ImageDto,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    return GridTile(
        modifier = modifier,
        enabled = enabled,
    ) {
        var isLoading by rememberSaveable { mutableStateOf(true) }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImage(
                model = item.thumbnailLink.toString(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                onError = { Timber.e(it.result.throwable) },
                onSuccess = { isLoading = false }
            )

            AnimatedVisibility(
                visible = isLoading,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}
