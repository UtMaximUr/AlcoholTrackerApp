package com.utmaximur.feature_settings.settings


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.domain.interactor.SettingsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import com.utmaximur.utils.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val settingsInteractor: SettingsInteractor) :
    ViewModel() {

    val checkedUpdateState: LiveData<Boolean> by lazy { MutableLiveData() }

    init {
        checkedUpdateState.setValue(settingsInteractor.isUpdateChecked())
    }

    fun onLightThemeChange(checked: Boolean) {
        if (checked) {
            settingsInteractor.saveThemeChecked(THEME_LIGHT)
        }
    }

    fun onDarkThemeChange(checked: Boolean) {
        if (checked) {
            settingsInteractor.saveThemeChecked(THEME_DARK)
        }
    }

    fun onUseDefaultThemeChange(checked: Boolean) {
        if (checked)
        settingsInteractor.saveThemeChecked(THEME_UNDEFINED)
    }

    fun onUpdateChange(checked: Boolean) {
        checkedUpdateState.setValue(checked)
        settingsInteractor.saveUpdateChecked(checked)
    }
}