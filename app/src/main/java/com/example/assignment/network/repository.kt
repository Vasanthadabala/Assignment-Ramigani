package com.example.assignment.network

import com.example.assignment.database.WeatherDao
import com.example.assignment.database.WeatherEntity
import com.example.assignment.network.dataModel.WeatherResponse

class WeatherRepository(
    private val apiClient: WeatherApiClient,
    private val weatherDao: WeatherDao
) {
    suspend fun getWeather(cityName: String): WeatherEntity {
        return try {
            val response = apiClient.getWeather(cityName)
            val weatherEntity = response.toWeatherEntity()  // Convert API response to Entity
            weatherDao.insertWeather(weatherEntity)  // Save to Room database
            weatherEntity
        } catch (e: Exception) {
            // Fallback to cached data if network fails
            weatherDao.getWeatherByCity(cityName) ?: throw Exception("No cached data available.")
        }
    }
}

// Extension function to convert API response to Room entity
private fun WeatherResponse.toWeatherEntity() = WeatherEntity(
    cityName = location.name,
    temperature = current.temp_c,
    description = current.condition.text,
    humidity = current.humidity
)
