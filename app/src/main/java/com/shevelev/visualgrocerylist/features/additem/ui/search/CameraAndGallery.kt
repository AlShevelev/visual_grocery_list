package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.additem.ui.search

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import timber.log.Timber

typealias CropLauncher =  ManagedActivityResultLauncher<CropImageContractOptions, CropImageView.CropResult>

@Composable
    internal fun createImageCropLauncher(
    bitmapResult: (Bitmap) -> Unit
): CropLauncher {
    val activity = LocalActivity.current as Activity

    val launcher =
        rememberLauncherForActivityResult(contract = CropImageContract()) { result ->
            if (result.isSuccessful) {
                result.uriContent?.let {

                    //getBitmap method is deprecated in Android SDK 29 or above so we need to do this check here
                    val bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images
                            .Media.getBitmap(activity.contentResolver, it)
                    } else {
                        val source = ImageDecoder
                            .createSource(activity.contentResolver, it)
                        ImageDecoder.decodeBitmap(source)
                    }

                    bitmapResult(bitmap)
                }

            } else {
                //If something went wrong you can handle the error here
                Timber.e(result.error, "ImageCropping error: ${result.error}")
            }
        }

    return launcher
}

@Composable
internal fun getCropImageOptions() = CropImageOptions(
    fixAspectRatio = true,
    toolbarColor = MaterialTheme.colorScheme.primary.toArgb(),
    toolbarTintColor = MaterialTheme.colorScheme.onPrimary.toArgb(),
    toolbarTitleColor = MaterialTheme.colorScheme.onPrimary.toArgb(),
    activityBackgroundColor = MaterialTheme.colorScheme.surface.toArgb(),
    backgroundColor = MaterialTheme.colorScheme.surface
        .copy(alpha = 0.5f).toArgb(),
)