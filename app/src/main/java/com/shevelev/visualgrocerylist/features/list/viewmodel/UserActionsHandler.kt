package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.viewmodel

internal interface UserActionsHandler {
    fun onCheckedChange(dbId: Long)

    fun onDeleteItemClick(dbId: Long)
}