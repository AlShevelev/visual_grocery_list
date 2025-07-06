package com.shevelev.visualgrocerylist.network.dto

import android.net.Uri

data class ImageDto(
    val thumbnailLink: Uri,
    val imageLink: Uri,
    
    val fileSize: Long,
    val mimeType: String,
    
    val thumbnailSize: ImageSizeDto,
    val originalSize: ImageSizeDto,
)