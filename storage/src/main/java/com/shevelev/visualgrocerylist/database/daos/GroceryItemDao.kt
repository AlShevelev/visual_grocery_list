package com.shevelev.visualgrocerylist.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shevelev.visualgrocerylist.database.entities.GroceryItem

@Dao
internal interface GroceryItemDao {
    @Insert
    suspend fun create(item: GroceryItem): Long

    @Query("SELECT * FROM grocery_item WHERE key_word LIKE :keyWord")
    suspend fun readByKeyWord(keyWord: String): List<GroceryItem>

    @Update
    suspend fun update(item: GroceryItem)

    @Delete
    suspend fun delete(item: GroceryItem)
}