package com.shevelev.visualgrocerylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.navigation.NavigatorImpl
import com.shevelev.visualgrocerylist.features.additem.ui.ScreenRoot as AddItemScreenRoot
import com.shevelev.visualgrocerylist.features.edititems.ui.ScreenRoot as EditItemsScreenRoot
import com.shevelev.visualgrocerylist.features.list.ui.ScreenRoot as ListScreenRoot
import com.shevelev.visualgrocerylist.features.searchimage.ui.ScreenRoot as SearchImageScreenRoot
import com.shevelev.visualgrocerylist.shared.ui.navigation.Route
import com.shevelev.visualgrocerylist.shared.ui.theme.VisualGroceryListTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            VisualGroceryListTheme(dynamicColor = false) {
                val backStack = remember { mutableStateListOf<Route>(Route.ListScreenRoute) }
                val navigator = remember { NavigatorImpl(backStack) }

                NavDisplay(
                    entryDecorators = listOf(
                        rememberSavedStateNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    backStack = backStack,
                    onBack = { navigator.backHard() },
                    entryProvider = { key ->
                        when (key) {
                            is Route.ListScreenRoute -> NavEntry(key) {
                                ListScreenRoot(navigator)
                            }

                            is Route.AddItemScreenRoute -> NavEntry(key) {
                                AddItemScreenRoot(navigator)
                            }

                            is Route.EditItemsScreenRoute -> NavEntry(key) {
                                EditItemsScreenRoot(navigator)
                            }

                            is Route.SearchImageRoute -> NavEntry(key) {
                                SearchImageScreenRoot(navigator, keyword = key.keyword)
                            }
                        }
                    }
                )
            }
        }
    }
}
