package com.utmaximur.alcoholtracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "icon_database")
data class Icon(
    @PrimaryKey
    var id: String,
    var icon: Int

)