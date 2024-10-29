package com.example.weatherapp.data.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeatherDao {
    @Insert
    suspend fun insert(weatherData: WeatherEntity)

    @Query("DELETE FROM weather_data")
    suspend fun clearAllWeatherData()

    @Query("SELECT * FROM weather_data ORDER BY id DESC")
    fun getAllWeatherData(): LiveData<List<WeatherEntity>>

    @Query("SELECT * FROM weather_data WHERE city = :city ORDER BY id DESC")
    fun getWeatherDataByCity(city: String): LiveData<List<WeatherEntity>>
}
