package com.utmaximur.alcoholtracker.data.dbo

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "track_database")
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

) : Parcelable