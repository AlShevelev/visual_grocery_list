package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions

@Composable
fun GridTile(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = { },
    content: @Composable BoxScope.() -> Unit,
) {
    val dimensions = LocalDimensions.current

    Box(
        modifier = modifier
            .padding(all = dimensions.paddingHalf)
            .fillMaxWidth()
            .border(
                width = dimensions.paddingOneUnit,
                color = MaterialTheme.colorScheme.onSurface,
            )
            .aspectRatio(1f)
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            )
    ) {
        content()
    }
}