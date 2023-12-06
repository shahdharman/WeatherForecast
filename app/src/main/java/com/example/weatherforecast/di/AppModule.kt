/*
* create room db,dao interface  and retrofit instances*/
package com.example.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.example.weatherforecast.data.WeatherDatabase
import com.example.weatherforecast.model.Weather
import com.example.weatherforecast.network.WeatherAPI
import com.example.weatherforecast.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase) = weatherDatabase.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context:Context) =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database")
            .fallbackToDestructiveMigration()
            .build()
    @Provides
    @Singleton
    fun providesOpenWeatherAPI(): WeatherAPI{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }
}

