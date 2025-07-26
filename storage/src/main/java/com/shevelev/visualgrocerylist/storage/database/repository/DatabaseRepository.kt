package com.shevelev.visualgrocerylist.storage.database.repository

import com.shevelev.visualgrocerylist.storage.database.entities.GroceryItem
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItem
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItemCombined

interface DatabaseRepository {
    suspend fun findGroceryItemByKeyWord(keyWord: String): List<GroceryItem>

    suspend fun addGroceryItem(keyWord: String, fileName: String): Long

    suspend fun addGroceryListItemToTop(groceryItemDbId: Long): Long

    suspend fun getGroceryListItemByGroceryItemId(groceryItemDbId: Long): GroceryListItem?

    suspend fun getAllGroceryListItemCombined(): List<GroceryListItemCombined>

    suspend fun moveGroceryListItemToTop(item: GroceryListItem)
}