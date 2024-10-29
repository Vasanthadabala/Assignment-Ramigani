package com.example.weatherapp.screens

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.data.roomdatabase.AppDatabase
import com.example.weatherapp.data.api.WeatherRepository

class WeatherViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherScreenViewModel::class.java)) {
            val database = AppDatabase.getDatabase(application)
            val repository = WeatherRepository(database.weatherDao())
            @Suppress("UNCHECKED_CAST")
            return WeatherScreenViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
