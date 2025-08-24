package com.shevelev.visualgrocerylist.storage.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItem
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItemCombined

@Dao
internal interface GroceryListItemDao {
    @Insert
    suspend fun create(item: GroceryListItem): Long

    @Query("SELECT * FROM grocery_list_item")
    suspend fun readAll(): List<GroceryListItem>

    @Query("SELECT * FROM grocery_list_item order by sorting_order")
    suspend fun readAllCombined(): List<GroceryListItemCombined>

    @Query("SELECT min(sorting_order) FROM grocery_list_item")
    suspend fun readMinOrder(): Long?

    @Query("SELECT * FROM grocery_list_item where grocery_item_id = :groceryItemId")
    suspend fun readByGroceryItemId(groceryItemId: Long): List<GroceryListItem>

    @Update
    suspend fun update(item: GroceryListItem)

    @Delete
    suspend fun delete(item: GroceryListItem)

    @Query("DELETE FROM grocery_list_item where grocery_item_id = :groceryItemId")
    suspend fun deleteByGroceryItemId(groceryItemId: Long)
}
