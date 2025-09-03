package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto

import com.shevelev.visualgrocerylist.features.additem.dto.GridItem

internal data class GridRow(
    val items: List<GridItem>,
) {
    val id: String by lazy { items.joinToString { it.id } }
}