package com.shevelev.visualgrocerylist.database.di

import com.shevelev.visualgrocerylist.database.repository.DatabaseRepository
import com.shevelev.visualgrocerylist.database.repository.DebugDatabaseRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    singleOf(::DebugDatabaseRepositoryImpl) { bind<DatabaseRepository>() }
}