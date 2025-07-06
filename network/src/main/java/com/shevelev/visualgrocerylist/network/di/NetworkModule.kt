package com.shevelev.visualgrocerylist.network.di

import com.shevelev.visualgrocerylist.network.api.SearchApi
import com.shevelev.visualgrocerylist.network.api.YandexSearchApi
import com.shevelev.visualgrocerylist.network.repository.SearchRepository
import com.shevelev.visualgrocerylist.network.repository.SearchRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val networkModule = module {
    factoryOf(::YandexSearchApi) { bind<SearchApi>() }

    factoryOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
}