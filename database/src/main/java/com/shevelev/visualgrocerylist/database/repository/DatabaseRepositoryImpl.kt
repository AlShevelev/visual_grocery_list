package com.shevelev.visualgrocerylist.database.repository

import android.content.Context
import androidx.room.Room
import com.shevelev.visualgrocerylist.database.database.VisualGroceryListDatabase
import com.shevelev.visualgrocerylist.database.entities.GroceryItem

internal class DatabaseRepositoryImpl(
    private val appContext: Context,
): DatabaseRepository {
    private val db by lazy {
        Room.databaseBuilder(
            appContext,
            VisualGroceryListDatabase::class.java, "visual-grocery-list-database"
        ).build()
    }

    override suspend fun findGroceryItemByKeyWord(keyWord: String): List<GroceryItem> =
        db.groceryItem.readByKeyWord(keyWord.lowercase())
}