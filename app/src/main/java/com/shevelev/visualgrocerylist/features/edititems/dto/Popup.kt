package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto

internal sealed interface Popup {
    data class NamePopup(
        val name: String,
        val item: GridItem,
    ) : Popup

    data object ImageSelectionPopup : Popup
}