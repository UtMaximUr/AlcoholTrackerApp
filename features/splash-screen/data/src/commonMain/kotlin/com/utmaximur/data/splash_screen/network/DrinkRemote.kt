package com.utmaximur.data.splash_screen.network

import kotlinx.serialization.Serializable

@Serializable
data class DrinkRemote(
    val id: String,
    val name: String,
    val icon: String,
    val photo: String
)