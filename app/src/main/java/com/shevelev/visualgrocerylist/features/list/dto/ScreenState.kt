package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.dto

internal sealed interface ScreenState {
    data object Loading: ScreenState

    data class Data(
        val items: List<GridItem>,
        val notePopup: NotePopup? = null,
        val clearListConfirmationDialogIsShown: Boolean = false,
    ): ScreenState
}