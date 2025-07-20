package com.shevelev.visualgrocerylist.storage.database.di

import com.shevelev.visualgrocerylist.storage.database.repository.DatabaseRepository
import com.shevelev.visualgrocerylist.storage.database.repository.DebugDatabaseRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    singleOf(::DebugDatabaseRepositoryImpl) { bind<DatabaseRepository>() }
}