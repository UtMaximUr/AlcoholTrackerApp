package com.utmaximur.data.kandinsky.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ImageStyleRemote(
    val image: String?,
    val name: String?,
    val title: String?,
    val titleEn: String?
)