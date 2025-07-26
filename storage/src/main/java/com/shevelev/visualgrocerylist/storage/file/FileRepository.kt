package com.shevelev.visualgrocerylist.storage.file

import android.net.Uri
import java.io.File

interface FileRepository {
    /**
     * Download a file by its Uri and return the file's name
     */
    suspend fun download(uri: Uri): Result<String>

    fun getFileByName(fileName: String): File
}