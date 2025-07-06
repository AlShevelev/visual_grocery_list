package com.shevelev.visualgrocerylist.network.repository

import com.shevelev.visualgrocerylist.network.dto.SearchResultDto

interface SearchRepository {
    suspend fun search(request: String): Result<SearchResultDto>
}