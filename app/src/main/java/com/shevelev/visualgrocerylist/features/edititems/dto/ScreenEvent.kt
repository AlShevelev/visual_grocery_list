package com.shevelev.visualgrocerylist.features.edititems.dto

internal sealed interface ScreenEvent {
    data object Close: ScreenEvent

    data object Error: ScreenEvent

    data class NavigateToSearchImage(
        val keyword: String,
    ): ScreenEvent
}