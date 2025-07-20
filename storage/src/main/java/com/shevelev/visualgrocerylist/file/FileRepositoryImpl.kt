package com.shevelev.visualgrocerylist.file

import android.content.Context
import android.net.Uri
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

                val fileName = UUID.randomUUID().toString()

                context.openFileOutput(fileName, 0).use {
                    it.write(fileContent)
                }

                fileName
            }
        }.onFailure {
            Timber.e(it)
        }
}