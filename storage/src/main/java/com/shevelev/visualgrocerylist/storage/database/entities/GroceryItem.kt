package com.shevelev.visualgrocerylist.storage.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_item")
data class GroceryItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "grocery_item_id")
    val id: Long,

    @ColumnInfo(name = "image_file")
    val imageFile: String,

    @ColumnInfo(name = "key_word")
    val keyWord: String
)