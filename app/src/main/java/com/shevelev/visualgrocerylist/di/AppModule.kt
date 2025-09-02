package com.shevelev.visualgrocerylist.di

import com.shevelev.visualgrocerylist.features.edititems.viewmodel.EditItemsViewModel
import com.shevelev.visualgrocerylist.features.list.viewmodel.ListViewModel
import com.shevelev.visualgrocerylist.features.searchimage.viewmodel.SearchImageViewModel
import com.shevelev.visualgrocerylist.shared.architecture.FlagsStorage
import com.shevelev.visualgrocerylist.features.additem.viewmodel.AddItemScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::AddItemScreenViewModel)

    viewModelOf(::EditItemsViewModel)

    viewModelOf(::ListViewModel)

    viewModelOf(::SearchImageViewModel)

    singleOf(::FlagsStorage)
}