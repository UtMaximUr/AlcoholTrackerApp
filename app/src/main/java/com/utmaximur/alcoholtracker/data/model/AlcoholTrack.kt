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
    val drink: String,
    val volume: String,
    val quantity: Int,
    val degree: String,
    val price: Float,
    val date: Long,
    var icon: String

) : Parcelable