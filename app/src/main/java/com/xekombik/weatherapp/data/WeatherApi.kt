package com.xekombik.weatherapp.data

import com.xekombik.weatherapp.domain.CurrentWeather
import com.xekombik.weatherapp.domain.DailyWeatherHistory
import com.xekombik.weatherapp.domain.FutureWeather
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("aqi") value: String
    ): CurrentWeather

    @GET("v1/history.json")
    suspend fun getDailyWeatherHistory(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("dt") data: String
    ): DailyWeatherHistory

    @GET("v1/future.json")
    suspend fun getFutureWeather(
        @Query("key") key: String,
        @Query("q") location: String,
        @Query("dt") data: String
    ): FutureWeather
}
