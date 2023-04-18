package com.xekombik.weatherapp.di

import com.xekombik.weatherapp.data.WeatherRepositoryImpl
import com.xekombik.weatherapp.domain.WeatherRepository
import com.xekombik.weatherapp.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl }

    viewModel { MainViewModel(get()) }
}