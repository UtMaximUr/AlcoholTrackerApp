package com.utmaximur.alcoholtracker

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.utmaximur.alcoholtracker.dagger.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.dagger.component.DaggerAlcoholTrackComponent
import com.utmaximur.alcoholtracker.dagger.module.RoomDatabaseModule
import com.utmaximur.alcoholtracker.ui.settings.view.*

open class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var alcoholTrackComponent: AlcoholTrackComponent

    private val sharedPrefs by lazy { getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreate() {
        super.onCreate()
        when (getSavedTheme()) {
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_UNDEFINED -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        }
        instance = this

        alcoholTrackComponent = DaggerAlcoholTrackComponent
            .builder()
            .roomDatabaseModule(RoomDatabaseModule(this))
            .build()
    }

    private fun getSavedTheme() = sharedPrefs?.getInt(KEY_THEME, THEME_UNDEFINED)
}