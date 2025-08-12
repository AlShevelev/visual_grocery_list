package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto

internal data class ScreenState(
    val loading: Boolean = false,
    val items: List<GridItem> = emptyList(),
    val namePopup: NamePopup? = null,
)