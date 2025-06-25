package com.shevelev.visualgrocerylist.network

class ApiKeyExtractor {
    private external fun getApiKey(id: Int, source: String): String

    fun getKey(): String {
        return getApiKey(2, BuildConfig.YANDEX_SEARCH_API_KEY)
    }

    companion object{
        init {
            // Load the library
            System.loadLibrary("native-lib")
        }
    }
}