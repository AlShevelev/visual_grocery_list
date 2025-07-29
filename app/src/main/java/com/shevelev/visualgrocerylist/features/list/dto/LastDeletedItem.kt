package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto

import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItemCombined

internal data class LastDeletedItem(
    val item: GroceryListItemCombined,
    val index: Int,
)