package com.shevelev.visualgrocerylist.storage.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shevelev.visualgrocerylist.storage.database.daos.GroceryItemDao
import com.shevelev.visualgrocerylist.storage.database.daos.GroceryListItemDao
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryItem
import com.shevelev.visualgrocerylist.storage.database.entities.GroceryListItem

@Database(
    version = 1,
    entities = [
        GroceryListItem::class,
        GroceryItem::class,
    ]
)
internal abstract class VisualGroceryListDatabase: RoomDatabase() {
    abstract val groceryItem: GroceryItemDao

    abstract val groceryListItem: GroceryListItemDao
}