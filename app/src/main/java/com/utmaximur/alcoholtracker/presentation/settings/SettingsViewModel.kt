package com.utmaximur.alcoholtracker.presentation.settings


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.domain.interactor.SettingsInteractor
import com.utmaximur.alcoholtracker.util.THEME_DARK
import com.utmaximur.alcoholtracker.util.THEME_LIGHT
import com.utmaximur.alcoholtracker.util.THEME_UNDEFINED
import com.utmaximur.alcoholtracker.util.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
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