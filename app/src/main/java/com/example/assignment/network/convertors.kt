package com.example.assignment.network

import androidx.room.TypeConverter
import com.example.assignment.network.dataModel.Condition
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromCondition(condition: Condition): String {
        return Gson().toJson(condition)
    }

    @TypeConverter
    fun toCondition(conditionString: String): Condition {
        val type = object : TypeToken<Condition>() {}.type
        return Gson().fromJson(conditionString, type)
    }
}
