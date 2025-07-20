package com.shevelev.visualgrocerylist.storage.database.repository

import com.shevelev.visualgrocerylist.storage.database.entities.GroceryItem

interface DatabaseRepository {
    suspend fun findGroceryItemByKeyWord(keyWord: String): List<GroceryItem>
}