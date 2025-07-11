package com.shevelev.visualgrocerylist.shared.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val paddingOneUnit: Dp = 1.dp,
    val paddingQuarter: Dp = 2.dp,
    val paddingHalf: Dp = 4.dp,
    val paddingSingle: Dp = 8.dp,
    val paddingSingleAndHalf: Dp = 12.dp,
    val paddingDouble: Dp = 16.dp,
)

val LocalDimensions = staticCompositionLocalOf<Dimensions> {
    Dimensions()
}
