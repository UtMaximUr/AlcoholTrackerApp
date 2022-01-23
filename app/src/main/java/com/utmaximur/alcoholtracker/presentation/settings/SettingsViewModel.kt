package com.utmaximur.alcoholtracker.presentation.settings


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.domain.interactor.SettingsInteractor
import com.utmaximur.alcoholtracker.util.THEME_DARK
import com.utmaximur.alcoholtracker.util.THEME_LIGHT
import com.utmaximur.alcoholtracker.util.THEME_UNDEFINED
import com.utmaximur.alcoholtracker.util.setValue
import javax.inject.Inject

class SettingsViewModel @Inject constructor(private val settingsInteractor: SettingsInteractor) :
    ViewModel() {

    val checkedThemeState: LiveData<Boolean> by lazy { MutableLiveData() }
    val checkedUpdateState: LiveData<Boolean> by lazy { MutableLiveData() }
    val themeLightCheckState: LiveData<Boolean> by lazy { MutableLiveData() }
    val themeDarkCheckState: LiveData<Boolean> by lazy { MutableLiveData() }

    init {
        checkedThemeState.setValue(settingsInteractor.isUseDefaultThemeChecked())
        checkedUpdateState.setValue(settingsInteractor.isUpdateChecked())
        themeLightCheckState.setValue(settingsInteractor.isUseLightThemeChecked())
        themeDarkCheckState.setValue(settingsInteractor.isUseDarkThemeChecked())
    }

    fun onLightThemeChange(checked: Boolean) {
        if (checked) {
            themeLightCheckState.setValue(checked)
            settingsInteractor.saveThemeChecked(THEME_LIGHT)
        }
        if (themeDarkCheckState.value!!) {
            themeDarkCheckState.setValue(!checked)
        }
    }

    fun onDarkThemeChange(checked: Boolean) {
        if (checked) {
            themeDarkCheckState.setValue(checked)
            settingsInteractor.saveThemeChecked(THEME_DARK)
        }
        if (themeLightCheckState.value!!) {
            themeLightCheckState.setValue(!checked)
        }
    }

    fun onUseDefaultThemeChange(checked: Boolean) {
        checkedThemeState.setValue(checked)
        settingsInteractor.saveThemeChecked(THEME_UNDEFINED)
    }

    fun onUpdateChange(checked: Boolean) {
        checkedUpdateState.setValue(checked)
        settingsInteractor.saveUpdateChecked(checked)
    }
}