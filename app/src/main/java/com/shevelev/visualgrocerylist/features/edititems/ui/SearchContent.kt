package com.shevelev.visualgrocerylist.features.edititems.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.features.edititems.dto.GridItem
import com.shevelev.visualgrocerylist.features.edititems.dto.ScreenState
import com.shevelev.visualgrocerylist.features.edititems.viewmodel.UserActionsHandler
import com.shevelev.visualgrocerylist.shared.ui.components.AsyncImageFile
import com.shevelev.visualgrocerylist.shared.ui.components.EmptySearchResult
import com.shevelev.visualgrocerylist.shared.ui.components.GridTile
import com.shevelev.visualgrocerylist.shared.ui.components.SearchTextField
import com.shevelev.visualgrocerylist.shared.ui.components.TileButton
import com.shevelev.visualgrocerylist.shared.ui.components.TileTitle
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchContent(
    searchQuery: String,
    screenState: ScreenState,
    onSearchQueryChange: (String) -> Unit,
    userActionsHandler: UserActionsHandler,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchTextField(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            enabled = !screenState.loading,
            loading = screenState.loading,
        )

        if (screenState.items.isEmpty()) {
            val mainText = if (searchQuery.isEmpty()) {
                R.string.try_to_add
            } else {
                R.string.try_adjusting_your_search
            }

            EmptySearchResult(
                modifier = Modifier.fillMaxSize(),
                text = LocalContext.current.getString(mainText)
            )
        } else {
            LazyVerticalGrid(
                contentPadding = PaddingValues(dimensions.paddingHalf),

                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    count = screenState.items.size,
                    key = { index -> screenState.items[index].id },
                    itemContent = { index ->
                        ItemTile(
                            item = screenState.items[index],
                            enabled = !screenState.loading,
                            userActionsHandler = userActionsHandler,
                        )
                    }
                )
            }
        }
    }
}

@Composable
internal fun ItemTile(
    item: GridItem,
    enabled: Boolean,
    userActionsHandler: UserActionsHandler,
    modifier: Modifier = Modifier,
) {
    val foregroundColor = MaterialTheme.colorScheme.onPrimary

    GridTile(
        modifier = modifier,
        enabled = false,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImageFile(
                image = item.imageFile,
                modifier = Modifier.fillMaxSize(),
                onError = { Timber.e(it) },
            )

            Column(
                modifier = Modifier.fillMaxSize(),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                TileTitle(
                    text = item.title,
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TileButton(
                        onClick = { userActionsHandler.onDeleteItemClick(item) },
                        tint = foregroundColor,

                        iconResId = R.drawable.ic_rounded_delete_24,
                        enabled = enabled,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    TileButton(
                        onClick = { userActionsHandler.onEditNameClick(item) },
                        tint = foregroundColor,
                        iconResId = R.drawable.ic_edit_24,
                        enabled = enabled,
                    )
                    TileButton(
                        onClick = { userActionsHandler.onEditImageClick(item) },
                        tint = foregroundColor,
                        iconResId = R.drawable.ic_photo_camera_24,
                        enabled = enabled,
                    )
                }
            }
        }
    }
}
