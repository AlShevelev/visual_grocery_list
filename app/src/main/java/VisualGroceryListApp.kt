package com.shevelev.visualgrocerylist

import android.app.Application
import com.shevelev.visualgrocerylist.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class VisualGroceryListApp: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin{
            androidLogger()
            androidContext(this@VisualGroceryListApp)
            modules(networkModule)
        }
    }
}