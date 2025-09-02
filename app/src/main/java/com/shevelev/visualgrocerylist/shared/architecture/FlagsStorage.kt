package com.shevelev.visualgrocerylist.shared.architecture

internal interface Flag {
    object MustRefreshList: Flag

    data class SearchedImage(
        val fileName: String,
    ): Flag
}

internal class FlagsStorage {
    private val flags = mutableMapOf<String, Any>()

    inline fun <reified T: Flag>setFlag(flag: T) {
        flags[getKey<T>()] = flag
    }

    inline fun <reified T: Flag>hasFlag(): T? {
        return flags.remove(getKey<T>()) as? T
    }

    private inline fun <reified T: Flag> getKey() =
        requireNotNull(T::class.qualifiedName) {
            "${T::class} must has a name"
        }
}