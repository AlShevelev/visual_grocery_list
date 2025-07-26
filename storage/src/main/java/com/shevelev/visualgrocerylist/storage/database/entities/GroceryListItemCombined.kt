package com.shevelev.visualgrocerylist.storage.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class GroceryListItemCombined(
    @Embedded val listItem: GroceryListItem,

    @Relation(
        parentColumn = "grocery_item_id",
        entityColumn = "grocery_item_id"
    )
    internal val groceryItemInternal: List<GroceryItem>
) {
    val groceryItem: GroceryItem
        get() = groceryItemInternal.first()
}
