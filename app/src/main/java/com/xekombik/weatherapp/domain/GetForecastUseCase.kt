package com.xekombik.weatherapp.domain

class GetForecastUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun getWeatherNextDaysHistory(location: String): List<ShortWeatherInf> {
        return weatherRepository.getForecast(location)
    }
}