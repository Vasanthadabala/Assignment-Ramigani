package com.example.assignment.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment.database.WeatherEntity
import kotlinx.coroutines.launch

open class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    var weather: WeatherEntity? = null
        private set

    fun fetchWeather(cityName: String) {
        viewModelScope.launch {
            try {
                weather = repository.getWeather(cityName)
            } catch (e: Exception) {
                weather = WeatherEntity(cityName, 0.0, "Error fetching data", 0)
            }
        }
    }
}
