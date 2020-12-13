package com.utmaximur.alcoholtracker.data.extension

import io.realm.RealmList

fun List<String?>.toRealmList(): RealmList<String?> {
    val list = RealmList<String?>()
    for (item in this) {
        list.add(item)
    }
    return list
}