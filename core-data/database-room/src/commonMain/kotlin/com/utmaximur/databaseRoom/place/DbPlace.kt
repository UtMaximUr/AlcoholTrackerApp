package com.utmaximur.databaseRoom.place

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbPlace(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val longitude: Double,
    val latitude: Double,
    val trackId: Long
)