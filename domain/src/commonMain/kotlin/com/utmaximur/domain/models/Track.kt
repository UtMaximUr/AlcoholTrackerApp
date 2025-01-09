package com.utmaximur.domain.models

import com.utmaximur.domain.ZERO_VALUE_L

data class Track(
    val id: Long = ZERO_VALUE_L,
    val drink: Drink,
    val volume: Float,
    val quantity: Int,
    val degree: Float,
    val event: String,
    val price: Float,
    val date: Long
) {
    val totalPrice = quantity * price
}