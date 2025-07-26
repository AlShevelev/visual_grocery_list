package com.shevelev.visualgrocerylist.storage.database.repository

import android.content.Context
import androidx.room.Room
import com.shevelev.visualgrocerylist.storage.database.database.VisualGroceryListDatabase
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryItem
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItem
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItemCombined

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

    override suspend fun addGroceryItem(keyWord: String, fileName: String): Long {
        val itemToCreate = GroceryItem(
            id = 0L,
            imageFile = fileName,
            keyWord = keyWord,
        )

        return db.groceryItem.create(itemToCreate)
    }

    override suspend fun addGroceryListItemToTop(groceryItemDbId: Long): Long {
        val minSortingOrder = db.groceryListItem.readMinOrder()

        val itemToAdd = GroceryListItem(
            id = 0,
            groceryItemId = groceryItemDbId,
            checked = false,
            note = null,
            order = minSortingOrder?.let { it - 1} ?: 0,
        )
        return db.groceryListItem.create(itemToAdd)
    }

    override suspend fun getGroceryListItemByGroceryItemId(
        groceryItemDbId: Long,
    ): GroceryListItem? =
        db.groceryListItem.readByGroceryItemId(groceryItemDbId).firstOrNull()

    override suspend fun getAllGroceryListItemCombined(): List<GroceryListItemCombined> =
        db.groceryListItem.readAllCombined()

    override suspend fun moveGroceryListItemToTop(item: GroceryListItem) {
        val newSoringOrder = db.groceryListItem.readMinOrder()?.let { it - 1} ?: 0

        val itemToUpdate = item.copy(order = newSoringOrder)

        db.groceryListItem.update(itemToUpdate)
    }
}