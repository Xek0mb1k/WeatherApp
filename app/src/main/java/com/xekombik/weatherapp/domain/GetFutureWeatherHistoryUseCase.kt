package com.xekombik.weatherapp.domain

class GetFutureWeatherHistoryUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun getWeatherNextDaysHistory(location: String, data: String): List<ShortWeatherInf> {
        return weatherRepository.getFutureWeatherHistory(location, data)
    }
}