package com.shevelev.visualgrocerylist.storage.file

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.graphics.scale
import java.io.File
import java.net.URL
import java.util.UUID
import kotlin.io.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

internal class FileRepositoryImpl(
    private val context: Context,
): FileRepository {
    override suspend fun download(uri: Uri): Result<String> =
        runCatching {
            withContext(Dispatchers.IO) {
                val javaUrl = URL(uri.toString())
                val fileContent = javaUrl.readBytes()

                val fileName = generateFileName()

                context.openFileOutput(fileName, 0).use {
                    it.write(fileContent)
                }

                fileName
            }
        }.onFailure {
            Timber.e(it)
        }

    override suspend fun save(bitmap: Bitmap): Result<String> = runCatching {
        withContext(Dispatchers.IO) {
            val fileName = generateFileName()

            // Aspect ratio is 1, so we can check a width only
            val bitmapToSave = if (bitmap.width > MAX_BITMAP_SIZE_PX) {
                bitmap.scale(MAX_BITMAP_SIZE_PX, MAX_BITMAP_SIZE_PX)
            } else {
                bitmap
            }

            context.openFileOutput(fileName, 0).use { stream ->
                bitmapToSave.compress(Bitmap.CompressFormat.WEBP, COMPRESSION_QUALITY, stream)
            }

            fileName
        }
    }.onFailure {
        Timber.e(it)
    }

    private fun generateFileName() = UUID.randomUUID().toString()

    override fun getFileByName(fileName: String): File = File(context.filesDir, fileName)

    override fun delete(file: File) {
        file.delete()
    }

    companion object {
        private const val MAX_BITMAP_SIZE_PX = 250

        private const val COMPRESSION_QUALITY = 75
    }
}