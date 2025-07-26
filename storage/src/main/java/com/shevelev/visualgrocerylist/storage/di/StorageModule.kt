package com.shevelev.visualgrocerylist.storage.di

import com.shevelev.visualgrocerylist.storage.file.FileRepository
import com.shevelev.visualgrocerylist.storage.file.FileRepositoryImpl
import com.shevelev.visualgrocerylist.storage.database.repository.DatabaseRepository
import com.shevelev.visualgrocerylist.storage.database.repository.DatabaseRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val storageModule = module {
    singleOf(::DatabaseRepositoryImpl) { bind<DatabaseRepository>() }

    singleOf(::FileRepositoryImpl) { bind<FileRepository>() }
}