package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.ui.search

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.GridItem
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.dto.ScreenState
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions

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

    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val activity = LocalActivity.current as Activity

    val imageCropLauncher =
        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
            if (result.isSuccessful) {
                result.uriContent?.let {

                    //getBitmap method is deprecated in Android SDK 29 or above so we need to do this check here
                    bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images
                            .Media.getBitmap(activity.contentResolver, it)
                    } else {
                        val source = ImageDecoder
                            .createSource(activity.contentResolver, it)
                        ImageDecoder.decodeBitmap(source)
                    }
                }

            } else {
                //If something went wrong you can handle the error here
                println("ImageCropping error: ${result.error}")
            }
        }

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
                                onClick = {
                                    val cropOptions = CropImageContractOptions(
                                        null,
                                        CropImageOptions(imageSourceIncludeCamera = false)
                                    )
                                    imageCropLauncher.launch(cropOptions)
                                },
                                enabled = !screenState.loading,
                            )
                            GridItem.MakePhoto -> CameraTile(
                                onClick = {
                                    val cropOptions = CropImageContractOptions(
                                        null,
                                        CropImageOptions(imageSourceIncludeGallery = false)
                                    )
                                    imageCropLauncher.launch(cropOptions)
                                },
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
