package com.shevelev.visualgrocerylist.features.edititems.dto

internal data class ScreenState(
    val loading: Boolean = false,
    val items: List<GridItem> = emptyList(),
    val popup: Popup? = null,
)