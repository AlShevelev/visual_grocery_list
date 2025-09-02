package com.shevelev.visualgrocerylist.features.searchimage.dto

internal sealed interface ScreenEvent {
    data object Error: ScreenEvent

    data object Close: ScreenEvent
}