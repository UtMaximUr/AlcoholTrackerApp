package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.domain.interactor.SettingsInteractor
import com.utmaximur.alcoholtracker.presentation.settings.SettingsViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsViewModelFactory @Inject constructor(
    private var settingsInteractor: SettingsInteractor
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(settingsInteractor) as T
    }
}