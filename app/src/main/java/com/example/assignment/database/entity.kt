package com.example.assignment.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")  // Ensure the table name matches the Dao
data class WeatherEntity(
    @PrimaryKey val cityName: String,
    val temperature: Double,
    val description: String,
    val humidity: Int
)
