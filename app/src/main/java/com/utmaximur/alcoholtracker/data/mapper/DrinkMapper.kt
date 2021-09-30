package com.utmaximur.alcoholtracker.data.mapper

import com.utmaximur.alcoholtracker.data.dbo.DrinkDBO
import com.utmaximur.alcoholtracker.domain.entity.Drink
import javax.inject.Inject


class DrinkMapper @Inject constructor() {

    fun map(dbo: DrinkDBO): Drink {
        return Drink(
            id = dbo.id,
            drink = dbo.drink,
            degree = dbo.degree,
            volume = dbo.volume,
            icon = dbo.icon,
            photo = dbo.photo
        )
    }

    fun map(domain: Drink): DrinkDBO {
        return DrinkDBO(
            id = domain.id,
            drink = domain.drink,
            degree = domain.degree,
            volume = domain.volume,
            icon = domain.icon,
            photo = domain.photo
        )
    }
}