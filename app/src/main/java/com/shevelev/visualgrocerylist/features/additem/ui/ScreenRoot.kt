package com.shevelev.visualgrocerylist.features.additem.ui

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.ScreenEvent
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.ui.SearchContent
import com.shevelev.visualgrocerylist.features.additem.viewmodel.AddItemScreenViewModel
import com.shevelev.visualgrocerylist.shared.ui.navigation.Route
import org.koin.androidx.compose.koinViewModel


@Composable
internal fun ScreenRoot(
    backStack: MutableList<Route>,
) {
    Scaffold(
        topBar = { AppBar(backStack) },
    ) { innerPadding ->
        Content(modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppBar(
    backStack: MutableList<Route>,
) {
    val context = LocalContext.current

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors()
            .copy(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
        title = { Text(context.getString(R.string.app_item)) },
        navigationIcon = {
            IconButton(onClick = {
                backStack.removeLastOrNull()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
    )
}

@Composable
internal fun Content(
    modifier: Modifier = Modifier,
    viewModel: AddItemScreenViewModel = koinViewModel(),
) {
    val searchResults by viewModel.screenState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    SearchContent(
        searchQuery = viewModel.searchQuery,
        screenState = searchResults,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
        onSearchTheInternetClick = viewModel::onSearchTheInternetClick,
        modifier = modifier,
    )

    LaunchedEffect(Unit) {
        viewModel.screenEvent.collect {
            when (it) {
                is ScreenEvent.Error -> Toast
                    .makeText(context, R.string.error_image_search, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
