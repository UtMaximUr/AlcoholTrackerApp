package com.utmaximur.domain.geocoder

data class Place(
    val id: Long = 0L,
    val title: String,
    val longitude: Double,
    val latitude: Double,
    val trackId: Long = 0L,
)