package com.shevelev.visualgrocerylist.storage.database.repository

import com.shevelev.visualgrocerylist.storage.database.entities.GroceryItem
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItem
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItemCombined

internal class DebugDatabaseRepositoryImpl: DatabaseRepository {
    val source = listOf(
        GroceryItem(
            id = 1,
            imageFile = "",
            keyWord = "vegetables",
        ),
        GroceryItem(
            id = 2,
            imageFile = "",
            keyWord = " fruits",
        ),
        GroceryItem(
            id = 3,
            imageFile = "",
            keyWord = "other",
        ),
        GroceryItem(
            id = 4,
            imageFile = "",
            keyWord = "bread",
        ),
    )

    override suspend fun getGroceryItemByKeyWord(keyWord: String): List<GroceryItem> {
        val kw = keyWord.lowercase()
        return source.filter { it.keyWord.contains(kw) }
    }

    override suspend fun getGroceryItemById(dbId: Long): GroceryItem? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGroceryItems(): List<GroceryItem> {
        TODO("Not yet implemented")
    }

    override suspend fun removeGroceryItem(item: GroceryItem) {
        TODO("Not yet implemented")
    }

    override suspend fun updateGroceryItem(item: GroceryItem): GroceryItem {
        TODO("Not yet implemented")
    }

    override suspend fun removeGroceryListItemByGroceryItemId(dbId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun addGroceryItem(keyWord: String, fileName: String): Long {
        TODO("Not yet implemented")
    }

    override suspend fun addGroceryListItemToTop(groceryItemDbId: Long): Long {
        TODO("Not yet implemented")
    }

    override suspend fun getGroceryListItemByGroceryItemId(groceryItemDbId: Long): GroceryListItem? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGroceryListItemCombined(): List<GroceryListItemCombined> {
        TODO("Not yet implemented")
    }

    override suspend fun moveGroceryListItemToTop(item: GroceryListItem) {
        TODO("Not yet implemented")
    }

    override suspend fun updateGroceryListItem(item: GroceryListItem) {
        TODO("Not yet implemented")
    }

    override suspend fun removeGroceryListItem(item: GroceryListItem) {
        TODO("Not yet implemented")
    }

    override suspend fun addGroceryListItem(item: GroceryListItem) {
        TODO("Not yet implemented")
    }
}