package com.xekombik.weatherapp.data

import android.annotation.SuppressLint
import com.xekombik.weatherapp.domain.CurrentWeather
import com.xekombik.weatherapp.domain.ShortWeatherInf
import com.xekombik.weatherapp.domain.WeatherRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


object WeatherRepositoryImpl : WeatherRepository {

    private const val apiKey = "aaa82c559ee1401eb1853533231104"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)


    @SuppressLint("SimpleDateFormat")
    override suspend fun getFutureWeatherHistory(
        location: String,
        data: String
    ): List<ShortWeatherInf> {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.time = sdf.parse(data) as Date


        val weatherHistory = mutableListOf<ShortWeatherInf>()

        for (i in 0 until 14){
            val time = sdf.format(calendar.time)
            val weather = weatherApi.getFutureWeather(apiKey, location, time)

            with(weather.forecast.forecastday[0].day){
                weatherHistory.add(
                    ShortWeatherInf(
                        time, condition, avgtemp_c, avgtemp_f
                    )
                )
            }

            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return weatherHistory
    }

    override suspend fun getWeatherTodayHistory(
        location: String,
        data: String
    ): List<ShortWeatherInf> {
        val weatherHistory = mutableListOf<ShortWeatherInf>()
        val weather = weatherApi.getDailyWeatherHistory(apiKey, location, data)
        for (dailyMoment in weather.forecast.forecastday[0].hour) {
            weatherHistory.add(
                ShortWeatherInf(
                    dailyMoment.time, dailyMoment.condition, dailyMoment.temp_c, dailyMoment.temp_f
                )
            )
        }

        return weatherHistory
    }

    override suspend fun getWeather(location: String): CurrentWeather {
        return weatherApi.getWeather(apiKey, location, "no")
    }
}
