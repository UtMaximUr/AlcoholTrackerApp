package com.utmaximur.domain.models

import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.ZERO_VALUE_D
import com.utmaximur.domain.ZERO_VALUE_L

data class Place(
    val id: Long = ZERO_VALUE_L,
    val title: String,
    val longitude: Double,
    val latitude: Double,
    val trackId: Long = ZERO_VALUE_L,
) {
    companion object {
        val EMPTY = Place(
            title = EMPTY_STRING,
            longitude = ZERO_VALUE_D,
            latitude = ZERO_VALUE_D,
        )
    }
}