package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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