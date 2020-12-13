package com.utmaximur.alcoholtracker.data.extension.conversion

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.storage.`object`.AlcoholTrackerRealmObject


fun AlcoholTrack.toRealmObject(): AlcoholTrackerRealmObject {

    return AlcoholTrackerRealmObject(
        id = id,
        drink = drink,
        volume = volume,
        quantity = quantity,
        degree = degree,
        price = price,
        date = date,
        icon = icon
    )
}