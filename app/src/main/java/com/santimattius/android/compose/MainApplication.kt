package com.santimattius.android.compose

import android.app.Application
import com.santimattius.android.compose.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            logger(AndroidLogger())
            modules(appModule)
        }
    }
}