package com.shevelev.visualgrocerylist.features.list.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow as IconShadow
import androidx.compose.ui.unit.dp
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.features.list.dto.GridItem
import com.shevelev.visualgrocerylist.shared.ui.components.AsyncImageFile
import com.shevelev.visualgrocerylist.shared.ui.components.GridTile
import com.shevelev.visualgrocerylist.shared.ui.components.TileButton
import com.shevelev.visualgrocerylist.shared.ui.components.TileTitle
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions
import timber.log.Timber


@Composable
internal fun GroceryListItemTile(
    item: GridItem,
    modifier: Modifier = Modifier,
    onCheckedChange: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit,
    onNoteClick: (Long) -> Unit,
) {
    val foregroundColor = MaterialTheme.colorScheme.onPrimary

    val dimensions = LocalDimensions.current

    GridTile(
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
                TileTitle(
                    text = item.title,
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = item.checked,
                        onCheckedChange = { onCheckedChange(item.dbId) },
                        colors = CheckboxDefaults.colors().copy(
                            uncheckedBorderColor = foregroundColor,
                        ),
                        modifier = Modifier
                            .dropShadow(
                                shape = CircleShape,
                                shadow = IconShadow(radius = 0.dp, alpha = 0.15f)
                            )
                    )
                    TileButton(
                        onClick = { onDeleteClick(item.dbId) },
                        tint = foregroundColor,
                        iconResId = R.drawable.ic_rounded_delete_24,
                        modifier = Modifier.padding(start = dimensions.paddingHalf)
                    )
                    TileButton(
                        onClick = { onNoteClick(item.dbId) },
                        tint = foregroundColor,
                        iconResId = R.drawable.ic_add_note_24,
                        hasBadge = item.hasNote,
                    )
                }
            }
        }
    }
}
