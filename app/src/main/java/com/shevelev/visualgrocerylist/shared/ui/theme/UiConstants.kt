package com.shevelev.visualgrocerylist.shared.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class UiConstants(
    val animationStandardDuration: Long = 300,
)

val LocalUiConstants = staticCompositionLocalOf<UiConstants> {
    UiConstants()
}
