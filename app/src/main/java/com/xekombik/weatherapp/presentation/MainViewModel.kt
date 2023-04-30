package com.xekombik.weatherapp.presentation

import androidx.lifecycle.ViewModel
import com.xekombik.weatherapp.domain.*

class MainViewModel(repository: WeatherRepository) : ViewModel() {

    private val getWeatherUseCase = GetWeatherUseCase(repository)
    private val getHourlyForecastUseCase = GetHourlyForecastUseCase(repository)
    private val getForecastUseCase = GetForecastUseCase(repository)
    private val getPlaceListUseCase = GetCityListUseCase(repository)


    suspend fun loadWeather(location: String): CurrentWeather {
        return getWeatherUseCase.getWeather(location)
    }

    suspend fun getHourlyForecast(location: String, data: String): List<ShortWeatherInf> {
        return getHourlyForecastUseCase.getHourlyForecast(location, data)
    }

    suspend fun getForecast(location: String): List<ShortWeatherInf> {
        return getForecastUseCase.getForecast(location)
    }

    suspend fun getPlace(city: String): List<String> {
        return getPlaceListUseCase.getCityList(city)
    }
}
