package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto

import java.io.File

internal data class GridItem(
    val id: String,
    val dbId: Long,
    val title: String,
    val imageFile: File,
)