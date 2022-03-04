package com.utmaximur.domain.interactor

import com.utmaximur.domain.repository.PreferencesRepository

import javax.inject.Inject

class PreferencesInteractor @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {

    fun saveUpdateChecked(checked: Boolean) =
        preferencesRepository.saveUpdateChecked(checked)

    fun saveThemeChecked(theme: Int) =
        preferencesRepository.saveThemeChecked(theme)

    fun isUpdateChecked() = preferencesRepository.isUpdateChecked()

    fun getSaveTheme() = preferencesRepository.getSaveTheme()
}