package com.utmaximur.alcoholtracker.data.extension.conversion

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.storage.`object`.AlcoholTrackerRealmObject

fun AlcoholTrackerRealmObject.toAlcoholTrack(): AlcoholTrack? {
    return AlcoholTrack(
        id = this.id,
        drink = this.drink,
        volume = this.volume,
        quantity = this.quantity,
        degree = this.degree,
        price = this.price,
        date = this.date,
        icon = this.icon
    )
}