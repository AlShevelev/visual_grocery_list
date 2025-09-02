package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.navigation

import com.shevelev.visualgrocerylist.shared.ui.navigation.Route

internal class NavigatorImpl(
    private val backstack: MutableList<Route>,
) : Navigator {
    private var behaviour: NavigatorBehaviour = getDefaultBehaviour()

    override fun navigateTo(route: Route) = behaviour.onNavigateTo(route, backstack)

    override fun back() = behaviour.onBack(backstack)

    override fun backHard() = behaviour.onBackHard(backstack)

    override fun registerBehaviour(behaviour: NavigatorBehaviour) {
        this.behaviour = behaviour
    }

    override fun unregisterBehaviour() {
        behaviour = getDefaultBehaviour()
    }

    private fun getDefaultBehaviour() = object : NavigatorBehaviour { }
}