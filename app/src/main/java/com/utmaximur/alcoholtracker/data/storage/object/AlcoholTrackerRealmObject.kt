package com.utmaximur.alcoholtracker.data.storage.`object`

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class AlcoholTrackerRealmObject(
    @PrimaryKey var id: String = "",
    var drink: String = "",
    var volume: String = "",
    var quantity: Int = 0,
    var degree: String = "",
    var price: Float = 0.0f,
    var date: Long = 0,
    var icon: Int = 0

) : RealmObject()

