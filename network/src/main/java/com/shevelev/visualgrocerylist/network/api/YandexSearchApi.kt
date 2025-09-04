package com.shevelev.visualgrocerylist.network.api

import com.google.protobuf.ByteString
import com.shevelev.visualgrocerylist.network.ApiKeyExtractor
import com.shevelev.visualgrocerylist.network.api.ImgSearchService.ImageSpec
import com.shevelev.visualgrocerylist.network.api.ImgSearchService.ImageSpec.ImageColor
import com.shevelev.visualgrocerylist.network.api.ImgSearchService.ImageSpec.ImageFormat
import com.shevelev.visualgrocerylist.network.api.ImgSearchService.ImageSpec.ImageOrientation
import com.shevelev.visualgrocerylist.network.api.ImgSearchService.ImageSpec.ImageSize
import io.grpc.ManagedChannelBuilder
import io.grpc.Metadata
import io.grpc.Metadata.ASCII_STRING_MARSHALLER
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import yandex.cloud.api.search.v2.SearchQueryOuterClass.SearchQuery
import yandex.cloud.api.search.v2.SearchQueryOuterClass.SearchQuery.FixTypoMode
import yandex.cloud.api.search.v2.SearchQueryOuterClass.SearchQuery.SearchType

internal class YandexSearchApi : SearchApi {
    override suspend fun search(request: String): ByteString {
        val channel = ManagedChannelBuilder.forAddress(HOST, PORT).let {
            it.useTransportSecurity()

            it.executor(Dispatchers.IO.asExecutor()).build()
        }

        val stub = ImageSearchServiceGrpcKt.ImageSearchServiceCoroutineStub(channel)

        val query = SearchQuery
            .newBuilder()
            .setQueryText(request)
            .setSearchType(SearchType.SEARCH_TYPE_COM)
            .setFixTypoMode(FixTypoMode.FIX_TYPO_MODE_ON)
            .build()

        val imageSpec = ImageSpec
            .newBuilder()
            .setFormat(ImageFormat.IMAGE_FORMAT_UNSPECIFIED)
            .setSize(ImageSize.IMAGE_SIZE_MEDIUM)
            .setOrientation(ImageOrientation.IMAGE_ORIENTATION_SQUARE)
            .setColor(ImageColor.IMAGE_COLOR_COLOR)
            .build()

        val request = ImgSearchService.ImageSearchRequest.newBuilder()
            .setQuery(query)
            .setImageSpec(imageSpec)
            .setDocsOnPage(DOCS_ON_PAGE)
            .setFolderId(FOLDER_ID)
            .build()

        val headers = Metadata().apply {
            this.put<String>(
                Metadata.Key.of(AUTHORIZATION_HEADER_KEY, ASCII_STRING_MARSHALLER),
                "$AUTHORIZATION_HEADER_VALUE ${ApiKeyExtractor().getKey()}",
            )
        }

        return stub.search(request, headers).rawData
    }

    companion object {
        private const val HOST = "searchapi.api.cloud.yandex.net"
        private const val PORT = 443

        private const val FOLDER_ID = "b1goop4kc51ljhcm8n94"

        private const val AUTHORIZATION_HEADER_KEY = "Authorization"
        private const val AUTHORIZATION_HEADER_VALUE = "Api-Key"

        private const val DOCS_ON_PAGE = 40L
    }
}