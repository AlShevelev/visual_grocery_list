package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto

import java.io.File

data class GridItem(
    val id: String,
    val dbId: Long,
    val imageFile: File,
    val title: String,
    val checked: Boolean
)