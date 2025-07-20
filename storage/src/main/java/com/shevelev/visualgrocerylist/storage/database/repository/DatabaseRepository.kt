package com.shevelev.visualgrocerylist.storage.database.repository

import com.shevelev.visualgrocerylist.storage.database.entities.GroceryItem

interface DatabaseRepository {
    suspend fun findGroceryItemByKeyWord(keyWord: String): List<GroceryItem>

    suspend fun addGroceryItem(keyWord: String, fileName: String): Long

    suspend fun addGroceryListItemToTop(groceryItemDbId: Long): Long
}