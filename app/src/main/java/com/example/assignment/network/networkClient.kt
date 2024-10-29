package com.example.assignment.network

import com.example.assignment.network.dataModel.WeatherResponse
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class WeatherApiClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    // Make the API call to fetch weather data
    suspend fun getWeather(cityName: String): WeatherResponse {
        return client.get("https://api.weatherapi.com/v1/current.json") {
            parameter("key", Constant.API_KEY)  // Use the centralized API key
            parameter("q", cityName)
        }.body()
    }
}
