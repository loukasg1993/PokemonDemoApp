package com.reydix.demoapp.common

import android.app.Application
import com.reydix.demoapp.di.networkModule
import com.reydix.demoapp.di.resultsModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class KotlinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KotlinApplication)
            modules(networkModule, resultsModule)
        }
    }
}
