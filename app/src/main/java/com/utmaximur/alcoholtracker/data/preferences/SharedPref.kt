package com.utmaximur.alcoholtracker.data.preferences

import android.content.SharedPreferences
import com.utmaximur.alcoholtracker.util.KEY_THEME
import com.utmaximur.alcoholtracker.util.KEY_UPDATE
import com.utmaximur.alcoholtracker.util.THEME_UNDEFINED

class SharedPref(private val sharedPreferences: SharedPreferences) {

    fun saveUpdateChecked(checked: Boolean) =
        sharedPreferences.edit()?.putBoolean(KEY_UPDATE, checked)?.apply()

    fun saveThemeChecked(theme: Int) =
        sharedPreferences.edit()?.putInt(KEY_THEME, theme)?.apply()

    fun isUpdateChecked() = sharedPreferences.getBoolean(KEY_UPDATE, true)

    fun getSelectedTheme() = sharedPreferences.getInt(KEY_THEME, THEME_UNDEFINED)

}