package com.shevelev.visualgrocerylist.network.api

import com.google.protobuf.ByteString

interface SearchApi {
    suspend fun search(request: String): ByteString
}