package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto

internal sealed interface ScreenEvent {
    data class ShowDeleteNotification(
        val itemName: String,
        val itemDbId: Long,
    ): ScreenEvent
}