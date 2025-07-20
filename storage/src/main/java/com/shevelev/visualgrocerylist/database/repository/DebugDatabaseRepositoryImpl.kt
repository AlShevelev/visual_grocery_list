package com.shevelev.visualgrocerylist.database.repository

import com.shevelev.visualgrocerylist.database.entities.GroceryItem

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

    override suspend fun findGroceryItemByKeyWord(keyWord: String): List<GroceryItem> {
        val kw = keyWord.lowercase()
        return source.filter { it.keyWord.contains(kw) }
    }
}