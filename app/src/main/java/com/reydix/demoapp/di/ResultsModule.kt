package com.reydix.demoapp.di

import com.reydix.demoapp.api.Repository
import com.reydix.demoapp.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val resultsModule = module{
    single { Repository(get()) }
    viewModel { MainViewModel(get()) }
}