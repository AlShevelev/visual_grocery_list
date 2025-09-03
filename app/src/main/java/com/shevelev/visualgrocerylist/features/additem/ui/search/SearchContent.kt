package com.shevelev.visualgrocerylist.features.additem.ui.search

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.canhub.cropper.CropImageContractOptions
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.features.additem.dto.GridItem
import com.shevelev.visualgrocerylist.features.additem.dto.ScreenState
import com.shevelev.visualgrocerylist.shared.ui.components.EmptySearchResult
import com.shevelev.visualgrocerylist.shared.ui.components.SearchTextField
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchContent(
    searchQuery: String,
    screenState: ScreenState,
    onSearchQueryChange: (String) -> Unit,
    onSearchTheInternetClick: () -> Unit,
    onDbItemClick: (GridItem.Db) -> Unit,
    onCapturedItemClick: (GridItem.Captured) -> Unit,
    onInternetItemClick: (GridItem.Internet) -> Unit,
    onBitmapCaptured: (Bitmap) ->  Unit,
    modifier: Modifier = Modifier,
) {
    val dimensions = LocalDimensions.current

    val imageCropLauncher = createImageCropLauncher {
        onBitmapCaptured(it)
    }

    val cropImageOptions = getCropImageOptions()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchTextField(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            enabled = !screenState.loading,
            loading = screenState.loading,
        )

        if (screenState.items.isEmpty()) {
            EmptySearchResult(
                modifier = Modifier.fillMaxSize(),
                text = LocalContext.current.getString(R.string.try_adjusting_your_search)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(dimensions.paddingHalf),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    count = screenState.items.size,
                    key = { index -> screenState.items[index].id },
                    itemContent = { index ->
                        val row = screenState.items[index]
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (item in row.items) {
                                when (item) {
                                    is GridItem.Db -> DbItemTile(
                                        item = item,
                                        enabled = !screenState.loading,
                                        onClick = onDbItemClick,
                                        modifier = Modifier.weight(0.5f),
                                    )
                                    is GridItem.Captured -> CapturedItemTile(
                                        item = item,
                                        enabled = !screenState.loading,
                                        onClick = onCapturedItemClick,
                                        modifier = Modifier.weight(0.5f),
                                    )
                                    is GridItem.Internet -> InternetItemTile(
                                        item = item,
                                        enabled = !screenState.loading,
                                        onClick = onInternetItemClick,
                                        modifier = Modifier.weight(0.5f),
                                    )
                                    is GridItem.SearchInternetAction -> SearchTheInternetTile(
                                        onClick = onSearchTheInternetClick,
                                        enabled = !screenState.loading && item.enabled,
                                        modifier = Modifier.weight(0.5f),
                                    )
                                    GridItem.GalleryAction -> GalleryTile(
                                        onClick = {
                                            val cropOptions = CropImageContractOptions(
                                                uri = null,
                                                cropImageOptions = cropImageOptions
                                                    .copy(imageSourceIncludeCamera = false)
                                            )
                                            imageCropLauncher.launch(cropOptions)
                                        },
                                        enabled = !screenState.loading,
                                        modifier = Modifier.weight(0.5f),
                                    )
                                    GridItem.MakePhotoAction -> CameraTile(
                                        onClick = {
                                            val cropOptions = CropImageContractOptions(
                                                uri = null,
                                                cropImageOptions = cropImageOptions
                                                    .copy(imageSourceIncludeGallery = false)
                                            )
                                            imageCropLauncher.launch(cropOptions)
                                        },
                                        enabled = !screenState.loading,
                                        modifier = Modifier.weight(0.5f),
                                    )
                                    GridItem.Empty -> EmptyTile(
                                        modifier = Modifier.weight(0.5f),
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
