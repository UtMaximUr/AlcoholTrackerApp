package com.utmaximur.databaseRoom.drink

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbDrink(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val icon: String,
    val photo: String
)