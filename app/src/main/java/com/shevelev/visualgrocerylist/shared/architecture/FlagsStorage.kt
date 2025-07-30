package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.architecture

internal enum class Flags {
    MUST_REFRESH_LIST,
}

internal interface FlagsStorageRead {
    fun hasFlag(flag: Flags): Boolean
}

internal interface FlagsStorage: FlagsStorageRead {
    fun setFlag(flag: Flags)
}

internal class FlagsStorageImpl: FlagsStorage {
    private val flags = mutableSetOf<Flags>()

    override fun setFlag(flag: Flags) {
        flags.add(flag)
    }

    override fun hasFlag(flag: Flags): Boolean = flags.remove(flag)
}