package com.shevelev.visualgrocerylist.network.dto

data class SearchResultDto(
    val request: String,
    val images: List<ImageDto>,
)
