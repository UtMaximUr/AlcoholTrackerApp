package com.utmaximur.alcoholtracker.domain.interactor

import com.utmaximur.alcoholtracker.data.repository.PreferencesRepository

import javax.inject.Inject

class SettingsInteractor @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {

    fun saveUpdateChecked(checked: Boolean) =
        preferencesRepository.saveUpdateChecked(checked)

    fun saveThemeChecked(theme: Int) =
        preferencesRepository.saveThemeChecked(theme)

    fun isUpdateChecked() = preferencesRepository.isUpdateChecked()
}