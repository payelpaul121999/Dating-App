package com.droid.datingapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DatingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DatingApp)
            // modules(appModule)
        }
    }
}
