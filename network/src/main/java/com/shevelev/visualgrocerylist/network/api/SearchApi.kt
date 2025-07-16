package com.shevelev.visualgrocerylist.network.api

import com.google.protobuf.ByteString

internal interface SearchApi {
    suspend fun search(request: String): ByteString
}