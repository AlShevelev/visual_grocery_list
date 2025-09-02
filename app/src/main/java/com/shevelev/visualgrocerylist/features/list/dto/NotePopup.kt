package com.shevelev.visualgrocerylist.features.list.dto

internal data class NotePopup(
    val dbId: Long,
    val title: String,
    val note: String?,
)