package com.shevelev.visualgrocerylist.shared.ui.navigation

sealed interface Route {
    data object ListScreenRoute: Route

    data object AddItemScreenRoute: Route
}
