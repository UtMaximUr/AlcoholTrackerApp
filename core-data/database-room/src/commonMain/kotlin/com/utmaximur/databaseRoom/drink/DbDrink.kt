package com.utmaximur.databaseRoom.drink

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity
data class DbDrink(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val icon: String,
    val photo: String,
    val position: Int? = null,
    val createdAt: LocalDateTime? = null
)

data class DbDrinkPosition(
    val id: Long,
    val position: Int
)