package com.utmaximur.alcoholtracker.data.storage.`object`

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DrinkRealmObject(
    @PrimaryKey var drink: String = "",
    var alcoholStrength: RealmList<String?> = RealmList(),
    var alcoholVolume: Int = 0,
    var icon: Int = 0,
    var image: Int = 0
) : RealmObject()