package com.example.encode

import android.app.Application
import timber.log.Timber

class CardApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}