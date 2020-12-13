package com.utmaximur.alcoholtracker.data.storage.service

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class StorageService(context: Context) {

    val realm: Realm

    init {
        Realm.init(context)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .name("alcohol_tracker.realm")
                .build()

        )
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .name("drinks.realm")
                .build()
        )
        realm = Realm.getDefaultInstance()
    }

    fun disconnect() = realm.close()
}