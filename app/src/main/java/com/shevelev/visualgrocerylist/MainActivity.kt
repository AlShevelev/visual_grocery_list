package com.shevelev.visualgrocerylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.shevelev.visualgrocerylist.features.list.ui.ListScreenRoot
import com.shevelev.visualgrocerylist.shared.ui.navigation.ListScreenRoute
import com.shevelev.visualgrocerylist.shared.ui.theme.VisualGroceryListTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VisualGroceryListTheme(dynamicColor = false) {
                val backStack = remember { mutableStateListOf<Any>(ListScreenRoute) }

                NavDisplay(
                    backStack = backStack,
                    //onBack = { backStack.removeLastOrNull() },
                    entryProvider = { key ->
                        when (key) {
                            is ListScreenRoute -> NavEntry(key) {
                                ListScreenRoot()
                            }

                            else -> {
                                error("Unknown route: $key")
                            }
                        }
                    }
                )
            }
        }
    }
}
