package com.xekombik.weatherapp.data

import com.xekombik.weatherapp.domain.CurrentWeather
import com.xekombik.weatherapp.domain.ShortWeatherInf
import com.xekombik.weatherapp.domain.WeatherRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WeatherRepositoryImpl : WeatherRepository {

    private const val apiKey = "9f9990f6468e4ee282d72005232604"
//    0620d325c4474180ae5193531232504
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)

    override suspend fun getForecast(location: String): List<ShortWeatherInf> {

        val forecast = mutableListOf<ShortWeatherInf>()

        val weather = weatherApi.getForecast(apiKey, location, 10, "no", "no")

        for (dayPosition in 0 until weather.forecast.forecastday.size) {
            with(weather.forecast.forecastday[dayPosition]) {
                forecast.add(
                    ShortWeatherInf(date, day.condition, day.avgtemp_c, day.avgtemp_f, true)
                )
            }
        }

        return forecast
    }

    override suspend fun getHourlyForecast(
        location: String,
        data: String
    ): List<ShortWeatherInf> {
        val forecastHourlyCurrentDay = mutableListOf<ShortWeatherInf>()
        val weather = weatherApi.getForecast(apiKey, location, 10, "no", "no")

        for (forecastDay in weather.forecast.forecastday)
            if (forecastDay.date == data)
                for (hour in forecastDay.hour)
                    forecastHourlyCurrentDay.add(
                        ShortWeatherInf(
                            hour.time,
                            hour.condition,
                            hour.temp_c,
                            hour.temp_f,
                            hour.is_day == 1
                        )
                    )

        return forecastHourlyCurrentDay
    }

    override suspend fun getWeather(location: String): CurrentWeather {
        return weatherApi.getWeather(apiKey, location, "no")
    }

    override suspend fun getCityList(city: String): List<String> {
        val placeList = mutableListOf<String>()
        val placesData = weatherApi.getPlaces(apiKey, city)

        for (placeItem in placesData){
            placeList.add("${placeItem.name}, ${placeItem.country}")
        }

        return placeList
    }
}
