package com.shevelev.visualgrocerylist

import android.app.Application
import timber.log.Timber

class VisualGroceryListApp: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}