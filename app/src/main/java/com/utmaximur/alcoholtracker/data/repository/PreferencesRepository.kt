package com.utmaximur.alcoholtracker.data.repository

import com.utmaximur.alcoholtracker.data.preferences.SharedPref

class PreferencesRepository(
    private val sharedPreferences: SharedPref
) {

    fun saveUpdateChecked(checked: Boolean) =
        sharedPreferences.saveUpdateChecked(checked)

    fun saveThemeChecked(theme: Int) =
        sharedPreferences.saveThemeChecked(theme)

    fun isUpdateChecked() = sharedPreferences.isUpdateChecked()

    fun getSelectedTheme() = sharedPreferences.getSelectedTheme()
}