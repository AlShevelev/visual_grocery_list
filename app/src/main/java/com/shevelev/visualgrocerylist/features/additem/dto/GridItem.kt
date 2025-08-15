package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto

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

    data object SearchInternet: GridItem(Long.MIN_VALUE.toString())

    data object Gallery: GridItem((Long.MIN_VALUE + 1).toString())

    data object MakePhoto: GridItem((Long.MIN_VALUE + 2).toString())
}