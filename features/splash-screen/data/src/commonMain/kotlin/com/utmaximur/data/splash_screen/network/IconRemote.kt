package com.utmaximur.data.splash_screen.network

import kotlinx.serialization.Serializable

@Serializable
data class IconRemote(
    val id: String,
    val url: String
)