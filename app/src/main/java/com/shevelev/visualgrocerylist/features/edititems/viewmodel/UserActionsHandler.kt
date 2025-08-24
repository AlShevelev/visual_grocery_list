package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.viewmodel

import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto.GridItem

internal interface UserActionsHandler {
    fun onDeleteItemClick(item: GridItem)

    fun onDeleteItemConfirmed(dbId: Long)

    fun onDeleteItemRejected()
}