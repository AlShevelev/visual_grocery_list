package com.shevelev.visualgrocerylist.features.searchimage.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.navigation.Navigator
import com.shevelev.visualgrocerylist.features.searchimage.dto.GridItem
import com.shevelev.visualgrocerylist.features.searchimage.dto.ScreenEvent
import com.shevelev.visualgrocerylist.features.searchimage.dto.ScreenState
import com.shevelev.visualgrocerylist.features.searchimage.viewmodel.SearchImageViewModel
import com.shevelev.visualgrocerylist.shared.ui.components.AsyncImageUrl
import com.shevelev.visualgrocerylist.shared.ui.components.EmptySearchResult
import com.shevelev.visualgrocerylist.shared.ui.components.EmptySearchResultLoading
import com.shevelev.visualgrocerylist.shared.ui.components.GridTile
import com.shevelev.visualgrocerylist.shared.ui.components.SearchTextField
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
internal fun ScreenRoot(
    navigator: Navigator,
    keyword: String,
) {
    Scaffold(
        topBar = { AppBar(navigator) },
    ) { innerPadding ->
        Content(
            navigator = navigator,
            keyword = keyword,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AppBar(
    navigator: Navigator,
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
        title = { Text(context.getString(R.string.searching_internet)) },
        navigationIcon = {
            IconButton(onClick = {
                navigator.back()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
    )
}

@Composable
internal fun Content(
    modifier: Modifier = Modifier,
    keyword: String,
    viewModel: SearchImageViewModel = koinViewModel(),
    navigator: Navigator,
) {
    val searchResults by viewModel.screenState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    SearchContent(
        screenState = searchResults,
        onGridClick = viewModel::onGridItemClick,
        modifier = modifier,
    )

    LaunchedEffect(Unit) {
        viewModel.init(keyword)
    }

    LaunchedEffect(Unit) {
        viewModel.screenEvent.collect {
            when (it) {
                is ScreenEvent.Error -> Toast
                    .makeText(
                        context,
                        R.string.error_network,
                        Toast.LENGTH_SHORT
                    )
                    .show()

                is ScreenEvent.Close -> navigator.back()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchContent(
    screenState: ScreenState,
    onGridClick: (GridItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchTextField(
            searchQuery = screenState.keyWord,
            onSearchQueryChange = { },
            enabled = false,
            loading = false,
        )

        if (screenState.items.isEmpty()) {
            if (screenState.loading) {
                EmptySearchResultLoading(
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                EmptySearchResult(
                    modifier = Modifier.fillMaxSize(),
                    text = LocalContext.current.getString(R.string.the_search_result_is_empty)
                )
            }
        } else {
            LazyVerticalGrid(
                contentPadding = PaddingValues(dimensions.paddingHalf),

                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    count = screenState.items.size,
                    key = { index -> screenState.items[index].id },
                    itemContent = { index ->
                        GridItemTile(
                            item = screenState.items[index],
                            enabled = !screenState.loading,
                            onClick = onGridClick,
                        )
                    }
                )
            }
        }
    }
}

@Composable
internal fun GridItemTile(
    item: GridItem,
    enabled: Boolean,
    onClick: (GridItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    return GridTile(
        modifier = modifier,
        enabled = enabled,
        onClick = { onClick(item) }
    ) {
        var isLoading by rememberSaveable { mutableStateOf(true) }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            AsyncImageUrl(
                image = item.imageLink,
                modifier = Modifier.fillMaxSize(),
                onError = { Timber.e(it) },
                onSuccess = { isLoading = false }
            )

            AnimatedVisibility(
                visible = isLoading,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}

