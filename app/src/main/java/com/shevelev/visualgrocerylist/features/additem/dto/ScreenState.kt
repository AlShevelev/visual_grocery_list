package com.shevelev.visualgrocerylist.features.additem.dto

import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.GridRow

internal data class ScreenState(
    val loading: Boolean = false,
    val items: List<GridRow> = emptyList(),
    val namePopup: NamePopup? = null,
)