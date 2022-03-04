package com.utmaximur.data.repository

import com.utmaximur.data.preferences.SharedPref
import com.utmaximur.domain.repository.PreferencesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPref
) : PreferencesRepository {

    override fun saveUpdateChecked(checked: Boolean) {
        sharedPreferences.saveUpdateChecked(checked)
    }

    override fun saveThemeChecked(theme: Int) {
        sharedPreferences.saveThemeChecked(theme)
    }

    override fun isUpdateChecked() = sharedPreferences.isUpdateChecked()

    override fun getSaveTheme() = sharedPreferences.getSaveTheme()

}