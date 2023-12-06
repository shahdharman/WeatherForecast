package com.example.weatherforecast.network

import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPI {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query(value = "q") query : String,
        @Query(value = "units") units : String = "imperial",
        @Query(value = "appid") appid : String = Constants.API_KEY
    ): Weather

}
