package com.utmaximur.createDrink

import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.domain.models.Icon

data class DrinkData(
    val name: String,
    val icon: Icon,
    val photo: String
) {

    class Builder {
        private var name: String = EMPTY_STRING
        private var icon: Icon = Icon.EMPTY
        private var photo: String = EMPTY_STRING

        fun setName(param: String) = apply { name = param }
        fun setIcon(param: Icon) = apply { icon = param }
        fun setPhoto(param: String) = apply { photo = param }

        fun build() = DrinkData(
            name = name,
            icon = icon,
            photo = photo
        )
    }
}