package com.shevelev.visualgrocerylist.features.edititems.dto

internal sealed interface Popup {
    data class NamePopup(
        val name: String,
        val item: GridItem,
    ) : Popup

    data class DeleteConfirmationPopup(
        val item: GridItem,
    ) : Popup

    data class ImageSelectionPopup(
        val item: GridItem,
    ) : Popup
}