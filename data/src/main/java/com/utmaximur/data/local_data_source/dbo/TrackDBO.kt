package com.utmaximur.data.local_data_source.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.utmaximur.data.utils.TRACK_DB_NAME

@Entity(tableName = TRACK_DB_NAME)
data class TrackDBO(

    @PrimaryKey
    var id: String,
    var drink: String,
    var volume: String,
    var quantity: Int,
    var degree: String,
    var event: String,
    var price: Float,
    var date: Long,
    var icon: String,
    var image: String

)