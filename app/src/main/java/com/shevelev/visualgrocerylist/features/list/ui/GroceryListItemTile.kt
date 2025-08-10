package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.shadow.Shadow as IconShadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.AsyncImageFile
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.GridTile
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.OutlinedText
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
    val textStrokeColor = MaterialTheme.colorScheme.onSurface

    val dimensions = LocalDimensions.current

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
                with(LocalDensity.current) {
                    OutlinedText(
                        text = item.title,
                        fillColor = foregroundColor,
                        outlineColor = textStrokeColor,
                        outlineDrawStyle = Stroke(width = dimensions.paddingThird.toPx()),
                        style = MaterialTheme.typography.titleMedium.copy(
                            shadow = Shadow(
                                color = textStrokeColor.copy(alpha = 0.5f),
                                offset = Offset(
                                    x = dimensions.paddingQuarter.toPx(),
                                    y = dimensions.paddingQuarter.toPx(),
                                ),
                                blurRadius = dimensions.paddingThird.toPx(),
                            )
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
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
                    IconButton(
                        onClick = { onDeleteClick(item.dbId) },
                        modifier = Modifier.padding(start = dimensions.paddingHalf)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_rounded_delete_24),
                            contentDescription = "",
                            tint = foregroundColor,
                            modifier = Modifier
                                .dropShadow(
                                    shape = CircleShape,
                                    shadow = IconShadow(radius = 25.dp, alpha = 0.8f)
                                )
                        )
                    }
                    IconButton(onClick = { onNoteClick(item.dbId) }) {
                        NoteButtonIcon(tint = foregroundColor)

                        if (item.hasNote) {
                            BadgedBox(
                                badge = { Badge(modifier = Modifier.size(10.dp)) }
                            ) {
                                NoteButtonIcon(tint = foregroundColor)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NoteButtonIcon(
    tint: Color,
) {
    Icon(
        painter = painterResource(R.drawable.ic_add_note_24),
        contentDescription = "",
        tint = tint,
        modifier = Modifier.dropShadow(
            shape = CircleShape,
            shadow = IconShadow(radius = 25.dp, alpha = 0.8f)
        )
    )
}