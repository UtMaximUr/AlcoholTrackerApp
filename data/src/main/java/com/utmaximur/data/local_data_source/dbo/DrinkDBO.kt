package com.utmaximur.data.local_data_source.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.utmaximur.data.local_data_source.conversion.Converters

@Entity(tableName = "drink_database")
data class DrinkDBO(
    @PrimaryKey
    var id: String,
    var drink: String,
    @TypeConverters(Converters::class)
    var degree: List<String>,
    @TypeConverters(Converters::class)
    var volume: List<String>,
    var icon: String,
    var photo: String
)