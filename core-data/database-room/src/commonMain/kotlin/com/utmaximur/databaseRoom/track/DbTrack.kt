package com.utmaximur.databaseRoom.track

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utmaximur.databaseRoom.drink.DbDrink

@Entity
data class DbTrack(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val quantity: Int,
    val degree: Float,
    val volume: Float,
    val event: String,
    val price: Float,
    val date: Long,
    @Embedded("drink_")
    val drink: DbDrink
)