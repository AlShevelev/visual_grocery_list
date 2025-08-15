package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.ScreenState
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.AsyncImageFile
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.AsyncImageUrl
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.GridTile
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.TileTitle
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchContent(
    searchQuery: String,
    screenState: ScreenState,
    onSearchQueryChange: (String) -> Unit,
    onSearchTheInternetClick: () -> Unit,
    onDbItemClick: (GridItem.Db) -> Unit,
    onInternetItemClick: (GridItem.Internet) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchTextField(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            enabled = !screenState.loading,
        )

        if (screenState.items.isEmpty()) {
            EmptySearchResult(
                modifier = Modifier.fillMaxSize(),
            )
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
                        when (val item = screenState.items[index]) {
                            is GridItem.Db -> DbItemTile(
                                item = item,
                                enabled = !screenState.loading,
                                onClick = onDbItemClick,
                            )
                            is GridItem.SearchInternet -> SearchTheInternetTile(
                                onClick = onSearchTheInternetClick,
                                enabled = !screenState.loading,
                            )
                            GridItem.Gallery -> GalleryTile(
                                onClick = {},
                                enabled = !screenState.loading,
                            )
                            GridItem.MakePhoto -> CameraTile(
                                onClick = {},
                                enabled = !screenState.loading,
                            )
                            is GridItem.Internet -> InternetItemTile(
                                item = item,
                                enabled = !screenState.loading,
                                onClick = onInternetItemClick,
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun SearchTextField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current

    BasicTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        singleLine = true,
        enabled = enabled,
        modifier = modifier
            .padding(horizontal = dimensions.paddingSingle)
            .padding(top = dimensions.paddingSingle)
            .fillMaxWidth(),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .border(
                        width = dimensions.paddingQuarter,
                        color = MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(percent = 30)
                    )
                    .height(50.dp)
                    .padding(dimensions.paddingSingleAndHalf)
            ) {
                if (!enabled) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(24.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = dimensions.paddingDouble)
                ) {
                    innerTextField()
                }

                if (searchQuery.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = "Clear search",
                        modifier = Modifier
                            .clickable(
                                enabled = enabled,
                                onClick = { onSearchQueryChange("") }
                            )
                    )
                }
            }
        }
    )
}

@Composable
private fun EmptySearchResult(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = context.getString(R.string.nothing_found),
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            text = context.getString(R.string.try_adjusting_your_search),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun DbItemTile(
    item: GridItem.Db,
    enabled: Boolean,
    onClick: (GridItem.Db) -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    return GridTile(
        modifier = modifier,
        enabled = enabled,
        onClick = {
            keyboardController?.hide()
            onClick(item)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            AsyncImageFile(
                image = item.imageFile,
                modifier = Modifier.fillMaxSize(),
                onError = { Timber.e(it) },
            )

            TileTitle(
                text = item.title,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun PredefinedButtonTile(
    onClick: () -> Unit,
    enabled: Boolean,
    @StringRes textResId: Int,
    @DrawableRes iconResId: Int,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val dimensions = LocalDimensions.current

    val keyboardController = LocalSoftwareKeyboardController.current

    return GridTile(
        modifier = modifier,
        enabled = enabled,
        onClick = {
            keyboardController?.hide()
            onClick()
        },
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.scale(1f).size(40.dp)
            )
            Spacer(modifier = Modifier.height(dimensions.paddingSingleAndHalf))
            Text(
                text = context.getString(textResId),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
private fun SearchTheInternetTile(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    PredefinedButtonTile(
        onClick = onClick,
        enabled = enabled,
        textResId = R.string.search_the_internet,
        iconResId = R.drawable.ic_internet_24,
        modifier = modifier,
    )
}

@Composable
private fun CameraTile(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    PredefinedButtonTile(
        onClick = onClick,
        enabled = enabled,
        textResId = R.string.make_photo,
        iconResId = R.drawable.ic_photo_camera_24,
        modifier = modifier,
    )
}

@Composable
private fun GalleryTile(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    PredefinedButtonTile(
        onClick = onClick,
        enabled = enabled,
        textResId = R.string.get_from_gallery,
        iconResId = R.drawable.ic_image_24,
        modifier = modifier,
    )
}

@Composable
private fun InternetItemTile(
    item: GridItem.Internet,
    enabled: Boolean,
    onClick: (GridItem.Internet) -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    return GridTile(
        modifier = modifier,
        enabled = enabled,
        onClick = {
            keyboardController?.hide()
            onClick(item)
        }
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
