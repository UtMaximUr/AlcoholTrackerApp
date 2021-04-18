package com.utmaximur.alcoholtracker.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "track_database")
data class AlcoholTrack(

    @PrimaryKey
    var id: String,
    var drink: String,
    var volume: String,
    var quantity: Int,
    var degree: String,
    var event: String,
    var price: Float,
    var date: Long,
    var icon: String

) : Parcelable