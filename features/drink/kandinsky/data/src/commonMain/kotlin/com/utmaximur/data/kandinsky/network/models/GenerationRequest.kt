package com.utmaximur.data.kandinsky.network.models

import com.utmaximur.data.kandinsky.network.ApiConstants
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationRequest(
    val type: String,
    val style: String,
    val width: Int,
    val height: Int,
    @SerialName("num_images")
    val numImages: Int,
    val generateParams: GenerateParamsRequest
) {
    companion object {
        fun createDefaultRequest(style: String?, query: String?) = GenerationRequest(
            type = ApiConstants.REQUEST_PARAMETER_TYPE_GENERATE,
            style = style.orEmpty(),
            width = ApiConstants.IMAGE_SIZE,
            height = ApiConstants.IMAGE_SIZE,
            numImages = ApiConstants.IMAGES_NUMBER,
            generateParams = GenerateParamsRequest(
                query = query.orEmpty()
            )
        )
    }
}

@Serializable
data class GenerateParamsRequest(
    val query: String
)