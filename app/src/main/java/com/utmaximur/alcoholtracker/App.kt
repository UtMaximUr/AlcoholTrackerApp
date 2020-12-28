package com.utmaximur.alcoholtracker

import android.app.Application
import com.utmaximur.alcoholtracker.dagger.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.dagger.component.DaggerAlcoholTrackComponent
import com.utmaximur.alcoholtracker.dagger.module.RoomDatabaseModule

open class App: Application() {

    companion object {
        lateinit var instance: App
    }
    lateinit var alcoholTrackComponent: AlcoholTrackComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        alcoholTrackComponent = DaggerAlcoholTrackComponent
            .builder()
            .roomDatabaseModule(RoomDatabaseModule(this))
            .build()
    }
}