package com.utmaximur.alcoholtracker.data.extension.conversion

import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.`object`.DrinkRealmObject

fun DrinkRealmObject.toDrink(): Drink? {
    return Drink(
        drink = this.drink,
        alcoholStrength = this.alcoholStrength,
        alcoholVolume = this.alcoholVolume,
        icon = this.icon,
        image = image
    )
}