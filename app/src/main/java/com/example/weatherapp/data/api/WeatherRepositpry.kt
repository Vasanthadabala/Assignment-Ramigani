package com.example.weatherapp.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weatherapp.data.roomdatabase.WeatherDao
import com.example.weatherapp.data.roomdatabase.WeatherEntity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class WeatherRepository(private val weatherDao: WeatherDao) {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun fetchWeatherData(city: String): WeatherEntity? {
        val url = URLBuilder().apply {
            protocol = URLProtocol.HTTPS
            host = "api.weatherapi.com"
            encodedPath = "/v1/current.json"
            parameters.append("key", "1bf29c8e339749a58c8163033242106") // Replace with your actual API key
            parameters.append("q", city)
        }.buildString()

        return try {
            val response: WeatherResponse = client.get(url).body()
            Log.d("WeatherRepository", "API Response: $response")

            WeatherEntity(
                city = response.location.name,
                country = response.location.country,
                temperature = response.current.temp_c,
                condition = response.current.condition.text,
                conditionIcon = response.current.condition.icon,
                humidity = response.current.humidity,
                precipitation = response.current.precip_in,
                wind = response.current.wind_kph
            ).also { weatherEntity ->
                clearAllWeatherData()
                insert(weatherEntity)
            }
        } catch (e: Exception) {
            Log.e("WeatherRepository", "Error fetching weather data", e)
            null
        }
    }

    private suspend fun insert(weatherData: WeatherEntity) {
        withContext(Dispatchers.IO) {
            try {
                Log.d("WeatherRepository", "Inserting weather data: $weatherData")
                weatherDao.insert(weatherData)
            } catch (e: Exception) {
                Log.e("WeatherRepository", "Error inserting weather data", e)
            }
        }
    }

    fun getWeatherDataByCity(city: String): LiveData<List<WeatherEntity>> {
        return weatherDao.getWeatherDataByCity(city)
    }

    private suspend fun clearAllWeatherData() {
        withContext(Dispatchers.IO) {
            try {
                Log.d("WeatherRepository", "Clearing all weather data")
                weatherDao.clearAllWeatherData()
            } catch (e: Exception) {
                Log.e("WeatherRepository", "Error clearing weather data", e)
            }
        }
    }

    val allWeatherData: LiveData<List<WeatherEntity>> = weatherDao.getAllWeatherData()
}