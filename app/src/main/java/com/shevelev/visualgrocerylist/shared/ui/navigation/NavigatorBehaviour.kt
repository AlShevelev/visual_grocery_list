package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.navigation

import com.shevelev.visualgrocerylist.shared.ui.navigation.Route

internal interface NavigatorBehaviour {
    fun onNavigateTo(route: Route, backstack: MutableList<Route>) {
        backstack.add(route)
    }

    fun onBack(backstack: MutableList<Route>) {
        backstack.removeLastOrNull()
    }

    /**
     * Back by system button or gesture
     */
    fun onBackHard(backstack: MutableList<Route>) {
        backstack.removeLastOrNull()
    }
}