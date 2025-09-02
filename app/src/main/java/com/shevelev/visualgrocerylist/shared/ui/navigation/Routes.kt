package com.shevelev.visualgrocerylist.shared.ui.navigation

sealed interface Route {
    data object ListScreenRoute: Route

    data object AddItemScreenRoute: Route

    data object EditItemsScreenRoute: Route

    data class SearchImageRoute(
        val keyword: String,
    ): Route
}
