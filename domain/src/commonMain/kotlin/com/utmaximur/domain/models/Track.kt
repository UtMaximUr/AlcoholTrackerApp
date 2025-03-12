package com.utmaximur.domain.models

import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.ZERO_VALUE
import com.utmaximur.domain.ZERO_VALUE_F
import com.utmaximur.domain.ZERO_VALUE_L

data class Track(
    val id: Long = ZERO_VALUE_L,
    val drink: Drink,
    val volume: Float,
    val quantity: Int,
    val degree: Float,
    val event: String,
    val price: Float,
    val date: Long,
) {
    val totalPrice = quantity * price
    companion object {
        val EMPTY = Track(
            drink = Drink.EMPTY,
            volume = ZERO_VALUE_F,
            quantity = ZERO_VALUE,
            degree = ZERO_VALUE_F,
            event = EMPTY_STRING,
            price = ZERO_VALUE_F,
            date = ZERO_VALUE_L
        )
    }
}