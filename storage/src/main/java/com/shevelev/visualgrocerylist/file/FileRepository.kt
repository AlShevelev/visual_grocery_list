package com.shevelev.visualgrocerylist.file

import android.net.Uri

interface FileRepository {
    /**
     * Download a file by its Uri and return the file's name
     */
    suspend fun download(uri: Uri): Result<String>
}