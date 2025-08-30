package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.viewmodel

import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto.GridItem

internal interface UserActionsHandler {
    fun onDeleteItemClick(item: GridItem)

    fun onDeleteItemConfirmed(dbId: Long)

    fun onDeleteItemRejected()

    fun onEditNameClick(item: GridItem)

    fun onEditNameConfirmed(newName: String, item: GridItem)

    fun onEditNameRejected()

    fun onEditImageClick(item: GridItem)

    fun onEditImageCameraSelected(item: GridItem)

    fun onEditImageGallerySelected(item: GridItem)

    fun onEditImageSearchSelected(item: GridItem)

    fun onEditImageRejected()

}