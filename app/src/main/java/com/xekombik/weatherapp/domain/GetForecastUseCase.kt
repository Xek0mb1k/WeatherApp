package com.xekombik.weatherapp.domain

class GetForecastUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun getForecast(location: String): List<ShortWeatherInf> {
        return weatherRepository.getForecast(location)
    }
}