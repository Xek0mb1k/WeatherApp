package com.xekombik.weatherapp.data

import com.xekombik.weatherapp.domain.CurrentWeather
import com.xekombik.weatherapp.domain.ShortWeatherInf
import com.xekombik.weatherapp.domain.WeatherRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WeatherRepositoryImpl: WeatherRepository {

    private const val apiKey = "aaa82c559ee1401eb1853533231104"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)


    override fun getWeatherNextDaysHistoryUseCase(): List<ShortWeatherInf> {
        TODO("Not yet implemented")
    }

    override fun getWeatherTodayHistoryUseCase(): List<ShortWeatherInf> {
        TODO("Not yet implemented")
    }

    override suspend fun getWeather(location: String): CurrentWeather {
        return weatherApi.getWeather(apiKey, location, "no")
    }




}
