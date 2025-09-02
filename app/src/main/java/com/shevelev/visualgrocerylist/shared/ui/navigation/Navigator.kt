package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.navigation

import com.shevelev.visualgrocerylist.shared.ui.navigation.Route

internal interface Navigator {
    fun navigateTo(route: Route)

    fun back()

    /**
     * Back by system button or gesture
     */
    fun backHard()

    fun registerBehaviour(behaviour: NavigatorBehaviour)

    fun unregisterBehaviour()
}