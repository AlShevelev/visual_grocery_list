package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto

import com.shevelev.visualgrocerylist.storage.database.entities.GroceryItem
import com.shevelev.visualgrocerylist.network.dto.ImageDto

internal sealed class GridItem(val id: String) {
    data class Db(
        val item: GroceryItem,
    ): GridItem(item.id.toString())

    data class Internet(
        val item: ImageDto,
    ): GridItem(item.id)

    data object SearchInternet: GridItem(Long.MIN_VALUE.toString())
}