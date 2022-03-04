package com.utmaximur.data.preferences

import android.content.SharedPreferences
import com.utmaximur.data.utils.*

class SharedPref(sharedPreferences: SharedPreferences) {

    private var updateValue by sharedPreferences.boolean(defaultValue = UPDATE_UNDEFINED)
    private var themeValue by sharedPreferences.int(defaultValue = THEME_UNDEFINED)

    fun saveUpdateChecked(updateValue: Boolean) {
        this.updateValue = updateValue
    }

    fun saveThemeChecked(themeValue: Int) {
        this.themeValue = themeValue
    }

    fun isUpdateChecked() = updateValue

    fun getSaveTheme() = themeValue

}