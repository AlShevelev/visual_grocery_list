package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.viewmodel

import android.graphics.Bitmap
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto.GridItem

internal interface UserActionsHandler {
    fun onDeleteItemClick(item: GridItem)

    fun onDeleteItemConfirmed()

    fun onDeleteItemRejected()

    fun onEditNameClick(item: GridItem)

    fun onEditNameConfirmed(newName: String)

    fun onEditNameRejected()

    fun onEditImageClick(item: GridItem)

    fun onEditImageSearchSelected()

    fun onEditImageDismissed()

    fun onBitmapCaptured(bitmap: Bitmap)
}