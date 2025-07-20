package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto

internal sealed interface ScreenEvent {
    data object Error: ScreenEvent

    data object Close: ScreenEvent
}