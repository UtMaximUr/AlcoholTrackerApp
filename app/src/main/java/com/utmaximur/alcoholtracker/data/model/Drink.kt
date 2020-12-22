package com.utmaximur.alcoholtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.utmaximur.alcoholtracker.data.extension.conversion.Converters

@Entity(tableName = "drink_database")
data class Drink(
    @PrimaryKey
    var id: String,
    var drink: String,
    @TypeConverters(Converters::class)
    var alcoholStrength: List<String?>,
    var alcoholVolume: Int,
    var icon: Int,
    var image: Int

)