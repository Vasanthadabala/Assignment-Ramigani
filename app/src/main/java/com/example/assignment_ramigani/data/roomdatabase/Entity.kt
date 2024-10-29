package com.example.assignment_ramigani.data.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val city: String,
    val country:String,
    val temperature: String,
    val condition: String,
    val conditionIcon: String,
    val humidity: String,
    val precipitation: String,
    val wind: String
)