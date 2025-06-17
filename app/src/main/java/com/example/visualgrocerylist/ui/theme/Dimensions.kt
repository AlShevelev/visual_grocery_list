package com.example.visualgrocerylist.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val zero: Dp = 0.dp,
)

val LocalDimensions = staticCompositionLocalOf<Dimensions> {
    Dimensions()
}
