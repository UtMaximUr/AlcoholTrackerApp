package com.utmaximur.alcoholtracker

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.utmaximur.alcoholtracker.util.*
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class App : MultiDexApplication() {

    private val sharedPrefs by lazy { getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreate() {
        super.onCreate()
        when (getSavedTheme()) {
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_UNDEFINED -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        }
    }

    private fun getSavedTheme() = sharedPrefs?.getInt(KEY_THEME, THEME_UNDEFINED)
}