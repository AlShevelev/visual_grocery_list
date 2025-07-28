package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto

import androidx.compose.runtime.Immutable
import java.io.File

@Immutable
data class GridItem(
    val id: String,
    val dbId: Long,
    val imageFile: File,
    val title: String,
    val checked: Boolean
)