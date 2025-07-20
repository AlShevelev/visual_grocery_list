package com.shevelev.visualgrocerylist.storage.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "grocery_list_item",
    foreignKeys = [
        ForeignKey(
            entity = GroceryItem::class,
            parentColumns = arrayOf("grocery_item_id"),
            childColumns = arrayOf("grocery_item_id"),
            onUpdate = ForeignKey.NO_ACTION,
            onDelete = ForeignKey.NO_ACTION,
        )]
)
data class GroceryListItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "grocery_list_item_id")
    val id: Long,

    @ColumnInfo(name = "grocery_item_id")
    val groceryItemId: Long,

    @ColumnInfo(name = "checked")
    val checked: Boolean,

    @ColumnInfo(name = "note")
    val note: String?
)