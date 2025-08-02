package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.ScreenState
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.viewmodel.UserActionsHandler
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.AsyncImageFile
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.GridTile
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions
import timber.log.Timber

@Composable
internal fun GroceryListPlaceholder(
    modifier: Modifier = Modifier,
    state: ScreenState,
    userActionsHandler: UserActionsHandler
) {
    when (state) {
        is ScreenState.Loading -> LoadingStub(modifier = modifier)
        is ScreenState.Data -> {
            if (state.items.isEmpty()) {
                EmptyListStub(modifier = modifier)
            } else {
                GroceryList(
                    items = state.items,
                    modifier = modifier,
                    userActionsHandler = userActionsHandler,
                )
            }
        }
    }
}

@Composable
private fun CenterStub(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        content()
    }
}

@Composable
private fun EmptyListStub(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    CenterStub(
        modifier = modifier,
    ) {
        Text(
            text = context.getString(R.string.empty_list),
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
private fun LoadingStub(
    modifier: Modifier = Modifier
) {
    CenterStub(
        modifier = modifier,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
private fun GroceryList(
    modifier: Modifier = Modifier,
    items: List<GridItem>,
    userActionsHandler: UserActionsHandler,
) {
    val dimensions = LocalDimensions.current

    LazyVerticalGrid(
        contentPadding = PaddingValues(dimensions.paddingHalf),

        columns = GridCells.Fixed(2),
        modifier = modifier,
    ) {
        items(
            count = items.size,
            key = { index -> items[index].id },
            itemContent = { index ->
                ItemTile(
                    item = items[index],
                    onCheckedChange = userActionsHandler::onCheckedChange,
                    onDeleteClick = userActionsHandler::onDeleteItemClick,
                    onNoteClick = userActionsHandler::onNoteClick,
                )
            }
        )
    }
}

@Composable
private fun ItemTile(
    item: GridItem,
    modifier: Modifier = Modifier,
    onCheckedChange: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit,
    onNoteClick: (Long) -> Unit,
) {
    val foregroundColor = MaterialTheme.colorScheme.onPrimary
    val textBackgroundColor = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.5f)
    val buttonsBackgroundColor = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.25f)

    return GridTile(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImageFile(
                image = item.imageFile,
                modifier = Modifier.fillMaxSize(),
                grayscale = item.checked,
                onError = { Timber.e(it) },
            )

            Column(
                modifier = Modifier.fillMaxSize(),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = item.title,
                    color = foregroundColor,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .background(
                            color = textBackgroundColor,
                        )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = buttonsBackgroundColor,
                        )
                ) {
                    Checkbox(
                        checked = item.checked,
                        onCheckedChange = { onCheckedChange(item.dbId) },
                        colors = CheckboxDefaults.colors().copy(
                            uncheckedBorderColor = foregroundColor,
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(onClick = { onDeleteClick(item.dbId) }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_delete_24),
                                contentDescription = "",
                                tint = foregroundColor,
                            )
                        }
                        IconButton(onClick = { onNoteClick(item.dbId) }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_add_note_24),
                                contentDescription = "",
                                tint = foregroundColor,
                            )
                        }
                    }
                }
            }
        }
    }
}
