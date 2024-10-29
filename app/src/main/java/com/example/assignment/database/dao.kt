package com.example.assignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity): Long

    @Query("SELECT * FROM weather WHERE cityName = :city LIMIT 1")
    suspend fun getWeatherByCity(city: String): WeatherEntity?
}

