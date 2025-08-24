package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto

internal sealed interface ScreenEvent {
    data object Close: ScreenEvent
}