package com.reydix.demoapp.di

import com.reydix.demoapp.api.ApiClient
import org.koin.dsl.module

val networkModule = module {
    single { ApiClient.create() }
}