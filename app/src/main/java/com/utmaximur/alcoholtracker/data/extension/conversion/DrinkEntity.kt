package com.utmaximur.alcoholtracker.data.extension.conversion

import com.utmaximur.alcoholtracker.data.extension.toRealmList
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.`object`.DrinkRealmObject


fun Drink.toRealmObject(): DrinkRealmObject {

    return DrinkRealmObject(
        drink = drink,
        alcoholStrength = alcoholStrength.toRealmList(),
        alcoholVolume = alcoholVolume,
        icon = icon,
        image = image
    )
}