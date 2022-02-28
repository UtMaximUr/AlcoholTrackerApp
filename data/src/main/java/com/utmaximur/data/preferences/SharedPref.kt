package com.utmaximur.data.preferences

import android.content.SharedPreferences
import com.utmaximur.data.utils.*

class SharedPref(private val sharedPreferences: SharedPreferences) {

    fun saveUpdateChecked(checked: Boolean) =
        sharedPreferences.edit()?.putBoolean(KEY_UPDATE, checked)?.apply()

    fun saveThemeChecked(theme: Int) =
        sharedPreferences.edit()?.putInt(KEY_THEME, theme)?.apply()

    fun isUpdateChecked() = sharedPreferences.getBoolean(KEY_UPDATE, UPDATE_UNDEFINED)

    fun getSaveTheme() = sharedPreferences.getInt(KEY_THEME, THEME_UNDEFINED)

}