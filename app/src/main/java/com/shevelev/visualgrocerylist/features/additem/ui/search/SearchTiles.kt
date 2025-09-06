package com.shevelev.visualgrocerylist.features.additem.ui.search

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shevelev.visualgrocerylist.R
import com.shevelev.visualgrocerylist.features.additem.dto.GridItem
import com.shevelev.visualgrocerylist.shared.ui.components.AsyncBitmap
import com.shevelev.visualgrocerylist.shared.ui.components.AsyncImageFile
import com.shevelev.visualgrocerylist.shared.ui.components.AsyncImageUrl
import com.shevelev.visualgrocerylist.shared.ui.components.GridTile
import com.shevelev.visualgrocerylist.shared.ui.components.TileTitle
import com.shevelev.visualgrocerylist.shared.ui.theme.LocalDimensions
import timber.log.Timber

@Composable
internal fun DbItemTile(
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
        val alpha = if (enabled) 1.0f else 0.25f
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha),
                modifier = Modifier.scale(1f).size(40.dp),
            )
            Text(
                text = context.getString(textResId),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(dimensions.paddingSingle)
            )
        }
    }
}

@Composable
internal fun SearchTheInternetTile(
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
internal fun CameraTile(
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
internal fun GalleryTile(
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
internal fun InternetItemTile(
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

@Composable
internal fun CapturedItemTile(
    item: GridItem.Captured,
    enabled: Boolean,
    onClick: (GridItem.Captured) -> Unit,
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
        AsyncBitmap(
            bitmap = item.bitmap,
            modifier = Modifier.fillMaxSize(),
            onError = { Timber.e(it) },
        )
    }
}

@Composable
internal fun EmptyTile(
    modifier: Modifier = Modifier,
) {
    return GridTile(
        modifier = modifier,
        enabled = false,
        showBorder = false,
    ) {
    }
}
