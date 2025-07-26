package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.ui.components

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import java.io.File

@Composable
fun AsyncImageUrl(
    modifier: Modifier = Modifier,
    image: Uri,
    onError: (Throwable) -> Unit = {},
    onSuccess: () -> Unit = {},
) {
    AsyncImage(
        model = image.toString(),
        contentDescription = null,
        modifier = modifier,
        onError = { onError(it.result.throwable) },
        onSuccess = { onSuccess() },
    )
}

@Composable
fun AsyncImageFile(
    modifier: Modifier = Modifier,
    image: File,
    onError: (Throwable) -> Unit = {},
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .build(),
        contentDescription = null,
        modifier = modifier,
        onError = { onError(it.result.throwable) },
    )
}