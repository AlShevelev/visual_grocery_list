package com.shevelev.visualgrocerylist.features.additem.ui.search

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
                            is GridItem.Captured -> CapturedItemTile(
                                item = item,
                                enabled = !screenState.loading,
                                onClick = onCapturedItemClick,
                            )
                            is GridItem.Internet -> InternetItemTile(
                                item = item,
                                enabled = !screenState.loading,
                                onClick = onInternetItemClick,
                            )
                            is GridItem.SearchInternetAction -> SearchTheInternetTile(
                                onClick = onSearchTheInternetClick,
                                enabled = !screenState.loading,
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
                            )
                        }
                    }
                )
            }
        }
    }
}
