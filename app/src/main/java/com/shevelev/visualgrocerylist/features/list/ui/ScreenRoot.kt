package com.shevelev.visualgrocerylist.features.list.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.shared.ui.navigation.Route
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalUiConstants
import com.shevelev.visualgrocerylist.shared.ui.theme.VisualGroceryListTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenRoot(
    backStack: MutableList<Route>,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = { DrawerContent() },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = { AppBar(drawerState) },
            floatingActionButton = { MainButton(backStack) }
        ) { innerPadding ->
            Content(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun MainButton(
    backStack: MutableList<Route>,
) {
    FloatingActionButton(
        content = { Icon(Icons.Filled.Add, contentDescription = "") },
        shape = CircleShape,
        onClick = { backStack.add(Route.AddItemScreenRoute) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    drawerState: DrawerState,
) {
    val context = LocalContext.current

    var showDropDownMenu by remember { mutableStateOf(false) }

    val uiConstants = LocalUiConstants.current

    val scope = rememberCoroutineScope()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
        title = { Text(context.getString(R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    if (drawerState.isClosed) {
                        drawerState.open()
                    } else {
                        drawerState.close()
                    }
                }
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Drawer")
            }
        },
        actions = {
            IconButton(onClick = { showDropDownMenu = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }

            DropdownMenu(
                expanded = showDropDownMenu,
                onDismissRequest = { showDropDownMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text(text = context.getString(R.string.clear_list)) },
                    leadingIcon = { Icon(Icons.Default.Clear, null) },
                    onClick = {
                        scope.launch {
                            delay(uiConstants.animationStandardDuration)
                            showDropDownMenu = false
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun DrawerContent() {
    val dimensions = LocalDimensions.current
    val context = LocalContext.current

    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .padding(horizontal = dimensions.paddingDouble)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(dimensions.paddingSingleAndHalf))
            Text(
                text = context.getString(R.string.app_name),
                modifier = Modifier.padding(dimensions.paddingDouble),
                style = MaterialTheme.typography.titleLarge,
            )

            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = dimensions.paddingSingle)
            )

            NavigationDrawerItem(
                label = { Text(context.getString(R.string.edit_grocery_items)) },
                selected = false,
                icon = { Icon(Icons.Outlined.Edit, contentDescription = null) },
                onClick = { /* Handle click */ }
            )
            NavigationDrawerItem(
                label = { Text(context.getString(R.string.settings)) },
                selected = false,
                icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                onClick = { /* Handle click */ },
            )
            Spacer(Modifier.height(dimensions.paddingSingleAndHalf))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DrawerContentPreview() {
    VisualGroceryListTheme {
        DrawerContent()
    }
}

@Composable
fun Content(modifier: Modifier = Modifier) {
    Text(
        text = "Hello",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun ContentPreview() {
    VisualGroceryListTheme {
        Content()
    }
}
