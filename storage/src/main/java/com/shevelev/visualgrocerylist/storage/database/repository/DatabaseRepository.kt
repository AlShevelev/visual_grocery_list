package com.shevelev.visualgrocerylist.storage.database.repository

import com.shevelev.visualgrocerylist.storage.database.entities.GroceryItem
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItem
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItemCombined

interface DatabaseRepository {
    suspend fun getGroceryItemByKeyWord(keyWord: String): List<GroceryItem>

    suspend fun getGroceryItemById(dbId: Long): GroceryItem?

    suspend fun getAllGroceryItems(): List<GroceryItem>

    suspend fun removeGroceryItem(item: GroceryItem)

    suspend fun updateGroceryItem(item: GroceryItem)

    suspend fun removeGroceryListItemByGroceryItemId(dbId: Long)

    suspend fun addGroceryItem(keyWord: String, fileName: String): Long

    suspend fun addGroceryListItemToTop(groceryItemDbId: Long): Long

    suspend fun getGroceryListItemByGroceryItemId(groceryItemDbId: Long): GroceryListItem?

    suspend fun getAllGroceryListItemCombined(): List<GroceryListItemCombined>

    suspend fun moveGroceryListItemToTop(item: GroceryListItem)

    suspend fun updateGroceryListItem(item: GroceryListItem)

    suspend fun removeGroceryListItem(item: GroceryListItem)

    suspend fun addGroceryListItem(item: GroceryListItem)
}