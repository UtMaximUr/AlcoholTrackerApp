package com.utmaximur.alcoholtracker.domain.interactor

import com.utmaximur.alcoholtracker.data.repository.PreferencesRepository
import com.utmaximur.alcoholtracker.util.THEME_DARK
import com.utmaximur.alcoholtracker.util.THEME_LIGHT
import com.utmaximur.alcoholtracker.util.THEME_UNDEFINED

import javax.inject.Inject

class SettingsInteractor @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {

    private var isUseDefaultTheme: Boolean = false
    private var isUseLightTheme: Boolean = false
    private var isUseDarkTheme: Boolean = false

    init {
        when (preferencesRepository.getSelectedTheme()) {
            THEME_DARK -> {
                isUseDarkTheme = !isUseDarkTheme
            }
            THEME_LIGHT -> {
                isUseLightTheme = !isUseLightTheme
            }
            THEME_UNDEFINED -> {
                isUseDefaultTheme = !isUseDefaultTheme
//                if (resources.configuration.uiMode and
//                    Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
//                ) {
//                    isUseDarkTheme = !isUseDarkTheme
//                } else {
//                    isUseLightTheme = !isUseLightTheme
//                }
            }
        }
    }

    fun saveUpdateChecked(checked: Boolean) =
        preferencesRepository.saveUpdateChecked(checked)

    fun saveThemeChecked(theme: Int) =
        preferencesRepository.saveThemeChecked(theme)

    fun isUpdateChecked() = preferencesRepository.isUpdateChecked()

    fun isUseDefaultThemeChecked() = isUseDefaultTheme

    fun isUseLightThemeChecked() = isUseLightTheme

    fun isUseDarkThemeChecked() = isUseDarkTheme
}