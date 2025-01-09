package com.utmaximur.domain.models

import com.utmaximur.domain.EMPTY_STRING

data class TrackData(
    val quantity: String,
    val volume: String,
    val degree: String,
    val event: String,
    val price: String,
    val date: String,
    val drink: Drink
) {

    class Builder {
        private var quantity: String = EMPTY_STRING
        private var volume: String = EMPTY_STRING
        private var degree: String = EMPTY_STRING
        private var event: String = EMPTY_STRING
        private var price: String = EMPTY_STRING
        private var date: String = EMPTY_STRING
        private var drink: Drink = Drink.EMPTY

        fun setQuantity(param: String) = apply { quantity = param }
        fun setVolume(param: String) = apply { volume = param }
        fun setDegree(param: String) = apply { degree = param }
        fun setEvent(param: String) = apply { event = param }
        fun setPrice(param: String) = apply { price = param }
        fun setDate(param: String) = apply { date = param }
        fun setDrink(param: Drink) = apply { drink = param }

        fun build() = TrackData(
            drink = drink,
            quantity = quantity,
            volume = volume,
            degree = degree,
            event = event,
            price = price,
            date = date
        )
    }
}