package com.utmaximur.data.kandinsky.network.models

import kotlinx.serialization.Serializable

@Serializable
data class FusionBrainVersion(
    val id: Int,
    val name: String,
    val type: String,
    val version: Double
)