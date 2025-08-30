package com.shevelev.visualgrocerylist.storage.file

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface FileRepository {
    /**
     * Downloads a file by its Uri and return the file's name
     */
    suspend fun download(uri: Uri): Result<String>

    /**
     * Saves the bitmap to a file and return the file's name
     */
    suspend fun save(bitmap: Bitmap): Result<String>

    fun getFileByName(fileName: String): File

    fun delete(file: File)
}