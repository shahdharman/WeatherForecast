package com.example.weatherforecast.screens.main
//view models take data from repository and provide to our main screen
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherforecast.data.DataOrException
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.model.WeatherItem
import com.example.weatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository):
    ViewModel() {
    suspend fun getWeatherData(city:String,units:String):
            DataOrException<Weather,Boolean,Exception>{

            return repository.getWeather(cityQuery = city,units = units)
    }
}