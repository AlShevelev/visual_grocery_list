package com.shevelev.visualgrocerylist.features.searchimage.dto


internal data class ScreenState(
    val loading: Boolean = false,
    val keyWord: String = "",
    val items: List<GridItem> = emptyList(),
)