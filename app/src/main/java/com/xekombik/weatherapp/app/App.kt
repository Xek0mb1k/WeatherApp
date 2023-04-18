package com.xekombik.weatherapp.app

import android.app.Application
import com.xekombik.weatherapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin
        startKoin{
            androidContext(this@App)
            modules(appModule)
        }
    }

}