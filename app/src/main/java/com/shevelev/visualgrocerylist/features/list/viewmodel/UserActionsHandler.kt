package com.shevelev.visualgrocerylist.features.list.viewmodel

internal interface UserActionsHandler {
    fun onCheckedChange(dbId: Long)

    fun onDeleteItemClick(dbId: Long)

    fun onRestoreDeletedItemClick(dbId: Long)

    fun onNoteClick(dbId: Long)

    fun onUpdateNote(dbId: Long, note: String)

    fun onNotePopupDismissed()

    fun onReorderItem(fromIndex: Int, toIndex: Int)

    fun onListClearConfirmationStarted()

    fun onListClearConfirmed()

    fun onListClearRejected()
}