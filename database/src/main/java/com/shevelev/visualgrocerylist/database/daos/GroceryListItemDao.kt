package com.shevelev.visualgrocerylist.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shevelev.visualgrocerylist.database.entities.GroceryListItem

@Dao
internal interface GroceryListItemDao {
    @Insert
    suspend fun create(item: GroceryListItem): Long

    @Query("SELECT * FROM grocery_list_item")
    suspend fun readAll(): List<GroceryListItem>

    @Update
    suspend fun update(item: GroceryListItem)

    @Delete
    suspend fun delete(item: GroceryListItem)
}