package com.example.weatherforecast.repository

/*
mediator - takes data from api or room db and provides to view model
used because any changes made to current api or db or if new data source is added
than changes will be made in repository
and view model wont be affected.
hence repository works as single source of truth
*/
import android.util.Log
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api:WeatherAPI){
    suspend fun getWeather(cityQuery:String,units:String): DataOrException<Weather,Boolean,Exception>{
        val response  = try{
            api.getWeather(query = cityQuery,units = units)
        }
        catch(e:Exception){
            Log.d("REX", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("INSIDE", "getWeather: $response")
        return DataOrException(data = response)
    }
}