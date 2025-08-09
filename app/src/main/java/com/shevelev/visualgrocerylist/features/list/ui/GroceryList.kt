package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.ScreenState
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.viewmodel.UserActionsHandler
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyGridState
import org.burnoutcrew.reorderable.reorderable

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

    val state = rememberReorderableLazyGridState(
        onMove = { from, to ->
            userActionsHandler.onReorderItem(from.index, to.index)
        })

    LazyVerticalGrid(
        contentPadding = PaddingValues(dimensions.paddingHalf),
        state = state.gridState,
        columns = GridCells.Fixed(2),
        modifier = modifier.reorderable(state)
    ) {
        items(
            count = items.size,
            key = { index -> items[index].id },
            itemContent = { index ->
                ReorderableItem(
                    state,
                    key = items[index].id,
                    defaultDraggingModifier = Modifier,
                    orientationLocked = false,
                ) { isDragging ->
                    GroceryListItemTile(
                        modifier = Modifier.detectReorderAfterLongPress(state),
                        item = items[index],
                        onCheckedChange = userActionsHandler::onCheckedChange,
                        onDeleteClick = userActionsHandler::onDeleteItemClick,
                        onNoteClick = userActionsHandler::onNoteClick,
                    )
                }
            }
        )
    }
}
