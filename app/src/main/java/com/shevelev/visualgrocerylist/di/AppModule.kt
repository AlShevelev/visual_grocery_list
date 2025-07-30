package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.di

import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.features.list.viewmodel.ListViewModel
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.architecture.FlagsStorageImpl
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.architecture.FlagsStorage
import com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.shared.architecture.FlagsStorageRead
import com.shevelev.visualgrocerylist.features.additem.viewmodel.AddItemScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.binds
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::AddItemScreenViewModel)

    viewModelOf(::ListViewModel)

    singleOf(::FlagsStorageImpl).binds(arrayOf(FlagsStorage::class, FlagsStorageRead::class))
}