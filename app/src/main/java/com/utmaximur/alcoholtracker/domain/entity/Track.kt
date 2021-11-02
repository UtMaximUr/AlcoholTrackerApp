package com.utmaximur.alcoholtracker.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(

    val id: String,
    val drink: String,
    val volume: String,
    val quantity: Int,
    val degree: String,
    val event: String,
    val price: Float,
    val date: Long,
    val icon: String,
    val image: String

) : Parcelable