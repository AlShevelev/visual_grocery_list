package com.shevelev.visualgrocerylist.di

import com.shevelev.visualgrocerylist.file.FileRepository
import com.shevelev.visualgrocerylist.file.FileRepositoryImpl
import com.shevelev.visualgrocerylist.storage.database.repository.DatabaseRepository
import com.shevelev.visualgrocerylist.storage.database.repository.DatabaseRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val storageModule = module {
    singleOf(::DatabaseRepositoryImpl) { bind<DatabaseRepository>() }

    singleOf(::FileRepositoryImpl) { bind<FileRepository>() }
}