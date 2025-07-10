package com.shevelev.visualgrocerylist.com.shevelev.visualgrocerylist.di

import com.shevelev.visualgrocerylist.features.additem.viewmodel.AddItemScreenViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::AddItemScreenViewModel)
}