package com.shevelev.visualgrocerylist.features.edititems.ui

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
import com.canhub.cropper.CropImageContractOptions
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.ui.search.createImageCropLauncher
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.ui.search.getCropImageOptions
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto.Popup
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.dto.ScreenEvent
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.ui.SearchContent
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.ui.SelectImageDialog
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.edititems.viewmodel.EditItemsViewModel
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.ConfirmationDialog
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components.NameConfirmationDialog
import com.shevelev.visualgrocerylist.shared.ui.navigation.Route
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ScreenRoot(
    backStack: MutableList<Route>,
) {
    Scaffold(
        topBar = { AppBar(backStack) },
    ) { innerPadding ->
        Content(
            backStack = backStack,
            modifier = Modifier.padding(innerPadding),
        )
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
        title = { Text(context.getString(R.string.edit_items)) },
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
    viewModel: EditItemsViewModel = koinViewModel(),
    backStack: MutableList<Route>,
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    val imageCropLauncher = createImageCropLauncher { viewModel.onBitmapCaptured(it) }
    val cropImageOptions = getCropImageOptions()

    val popup = screenState.popup
    when (popup) {
        is Popup.DeleteConfirmationPopup -> {
            val context = LocalContext.current

            ConfirmationDialog(
                text = context.getString(R.string.item_will_be_removed, popup.item.title),
                onConfirmation = {viewModel.onDeleteItemConfirmed()},
                onDismiss = viewModel::onDeleteItemRejected
            )
        }

        is Popup.NamePopup -> {
            NameConfirmationDialog(
                item = popup.item,
                startName = popup.name,
                onDismiss = viewModel::onEditNameRejected,
                onConfirmation = { name, item -> viewModel.onEditNameConfirmed(name) },
            )
        }

        is Popup.ImageSelectionPopup -> {
            SelectImageDialog(
                onDismiss = viewModel::onEditImageDismissed,
                onCamera = {
                    viewModel.onEditImageDismissed()

                    val cropOptions = CropImageContractOptions(
                        uri = null,
                        cropImageOptions = cropImageOptions
                            .copy(imageSourceIncludeGallery = false)
                    )
                    imageCropLauncher.launch(cropOptions)
                },
                onGallery = {
                    viewModel.onEditImageDismissed()

                    val cropOptions = CropImageContractOptions(
                        uri = null,
                        cropImageOptions = cropImageOptions
                            .copy(imageSourceIncludeCamera = false)
                    )
                    imageCropLauncher.launch(cropOptions)
                },
                onSearch = { viewModel.onEditImageSearchSelected() }
            )
        }

        else -> { }
    }

    SearchContent(
        searchQuery = viewModel.searchQuery,
        screenState = screenState,
        onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
        modifier = modifier,
        userActionsHandler = viewModel,
    )

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.screenEvent.collect {
            when (it) {
                is ScreenEvent.Error -> Toast
                    .makeText(context, R.string.error_network, Toast.LENGTH_SHORT)
                    .show()

                is ScreenEvent.Close -> backStack.removeLastOrNull()
            }
        }
    }
}
