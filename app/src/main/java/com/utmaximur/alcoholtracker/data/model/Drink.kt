package com.utmaximur.alcoholtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.utmaximur.alcoholtracker.data.conversion.Converters

@Entity(tableName = "drink_database")
data class Drink(
    @PrimaryKey
    var id: String,
    var drink: String,
    @TypeConverters(Converters::class)
    var degree: List<String?>,
    @TypeConverters(Converters::class)
    var volume: List<String?>,
    var icon: String,
    var photo: String

)