package com.shevelev.visualgrocerylist.features.additem.dto

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

internal sealed class GridItem(val id: String) {
    class Db(
        id: String,
        val dbId: Long,
        val title: String,
        val imageFile: File,
    ): GridItem(id)

    class Internet(
        id: String,
        val imageLink: Uri,
    ): GridItem(id)

    class Captured(
        id: String,
        val bitmap: Bitmap,
    ): GridItem(id)

    data class SearchInternetAction(
        val enabled: Boolean,
    ): GridItem(Long.MIN_VALUE.toString())

    data object GalleryAction: GridItem((Long.MIN_VALUE + 1).toString())

    data object MakePhotoAction: GridItem((Long.MIN_VALUE + 2).toString())

    data object Empty: GridItem((Long.MIN_VALUE + 3).toString())
}