package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.shadow.Shadow as IconShadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions

@Composable
fun TileTitle(
    text: String,
    modifier: Modifier = Modifier,
    fillColor: Color = MaterialTheme.colorScheme.onPrimary,
    outlineColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    val dimensions = LocalDimensions.current

    with(LocalDensity.current) {
        OutlinedText(
            text = text,
            fillColor = fillColor,
            outlineColor = outlineColor,
            outlineDrawStyle = Stroke(width = dimensions.paddingThird.toPx()),
            style = MaterialTheme.typography.titleMedium.copy(
                shadow = Shadow(
                    color = outlineColor.copy(alpha = 0.5f),
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
            modifier = modifier
        )
    }
}

@Composable
internal fun IconInTileButton(
    tint: Color,
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(iconResId),
        contentDescription = "",
        tint = tint,
        modifier = modifier
            .dropShadow(
                shape = CircleShape,
                shadow = IconShadow(radius = 25.dp, alpha = 0.8f)
            )
    )
}

@Composable
fun TileButton(
    onClick: () -> Unit,
    tint: Color,
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier,
    hasBadge: Boolean = false,
    enabled: Boolean = true,
) {

    IconButton(
        onClick = {
            if (enabled) {
                onClick()
            }
        },
        modifier = modifier,
    ) {
        if (hasBadge) {
            BadgedBox(
                badge = { Badge(modifier = Modifier.size(10.dp)) }
            ) {
                IconInTileButton(
                    tint = tint,
                    iconResId = iconResId,
                )
            }
        } else {
            IconInTileButton(
                tint = tint,
                iconResId = iconResId,
            )
        }
    }
}
