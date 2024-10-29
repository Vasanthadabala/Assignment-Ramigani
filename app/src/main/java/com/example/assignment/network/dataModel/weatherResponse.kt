package com.example.assignment.network.dataModel

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val current: Current,
    val location: Location
)