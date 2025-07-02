package com.utmaximur.alcohol_calculator.models

import com.utmaximur.domain.ZERO_VALUE
import com.utmaximur.domain.ZERO_VALUE_D

data class Drink(
    val alcoholPercentage: Double,
    val volume: Int,
) {
    companion object {
        val EMPTY = Drink(
            alcoholPercentage = ZERO_VALUE_D,
            volume = ZERO_VALUE,
        )
    }
}