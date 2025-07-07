package com.shevelev.visualgrocerylist.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shevelev.visualgrocerylist.database.daos.GroceryItemDao
import com.shevelev.visualgrocerylist.database.daos.GroceryListItemDao
import com.shevelev.visualgrocerylist.database.entities.GroceryItem
import com.shevelev.visualgrocerylist.database.entities.GroceryListItem

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