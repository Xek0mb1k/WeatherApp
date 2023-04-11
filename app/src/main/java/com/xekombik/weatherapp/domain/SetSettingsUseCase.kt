package com.xekombik.weatherapp.domain

class SetSettingsUseCase(private val weatherRepository: WeatherRepository) {
    fun setSettings(useTempCel: Boolean, useKph: Boolean){
        weatherRepository.setSettings(useTempCel, useKph)
    }
}