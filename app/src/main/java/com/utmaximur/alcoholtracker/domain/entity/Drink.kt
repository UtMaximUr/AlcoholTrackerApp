package com.utmaximur.alcoholtracker.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Drink(

    val id: String,
    val drink: String,
    val degree: List<String>,
    val volume: List<String>,
    val icon: String,
    val photo: String

) : Parcelable