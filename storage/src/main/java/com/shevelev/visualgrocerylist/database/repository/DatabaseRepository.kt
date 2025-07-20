package com.shevelev.visualgrocerylist.database.repository

import com.shevelev.visualgrocerylist.database.entities.GroceryItem

interface DatabaseRepository {
    suspend fun findGroceryItemByKeyWord(keyWord: String): List<GroceryItem>
}