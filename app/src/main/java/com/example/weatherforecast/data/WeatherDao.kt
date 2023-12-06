package com.example.weatherforecast.data

import androidx.room.*
import com.example.weatherforecast.model.Favourite
import com.example.weatherforecast.model.Unit
import kotlinx.coroutines.flow.Flow
/*
DAO stands for Data Access Object and is a Kotlin class that provides access to the data.
* Specifically, the DAO is where you would include functions for reading and manipulating
* data. Calling a function on the DAO is the equivalent of performing a SQL command on
the database
*/
@Dao
interface WeatherDao {
    @Query("SELECT * from fav_tbl")
    //Flow is used to return a stream of data : https://developer.android.com/codelabs/basic-android-kotlin-training-intro-room-flow#0
    fun getFavourites(): Flow<List<Favourite>>

    @Query("SELECT * from fav_tbl where city =:city")
    suspend fun getFavById(city: String): Favourite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourite: Favourite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavourite(favourite: Favourite)

    @Query("DELETE from fav_tbl")
    suspend fun deleteAllFavourites()

    @Delete
    suspend fun deleteFavourite(favourite: Favourite)

    // Unit table`
    @Query("SELECT * from settings_tbl")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE from settings_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: Unit)
}