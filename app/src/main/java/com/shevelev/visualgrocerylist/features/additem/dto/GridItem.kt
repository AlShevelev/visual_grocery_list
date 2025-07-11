package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto

import com.shevelev.visualgrocerylist.database.entities.GroceryItem

internal sealed class GridItem(val id: String) {
    data class Db(
        val item: GroceryItem,
    ): GridItem(item.id.toString())

    data object SearchInternet: GridItem(Long.MIN_VALUE.toString())
}