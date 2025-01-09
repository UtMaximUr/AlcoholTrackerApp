package com.utmaximur.databaseRoom.icon

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DbIcon(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val url: String
)