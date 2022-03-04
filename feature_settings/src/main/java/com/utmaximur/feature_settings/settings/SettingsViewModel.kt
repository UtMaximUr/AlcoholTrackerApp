package com.utmaximur.feature_settings.settings


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.domain.interactor.PreferencesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import com.utmaximur.utils.*
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val preferencesInteractor: PreferencesInteractor) :
    ViewModel() {

    val checkedUpdateState: LiveData<Boolean> by lazy { MutableLiveData() }

    init {
        checkedUpdateState.setValue(preferencesInteractor.isUpdateChecked())
    }

    fun onLightThemeChange(checked: Boolean) {
        if (checked) {
            preferencesInteractor.saveThemeChecked(THEME_LIGHT)
        }
    }

    fun onDarkThemeChange(checked: Boolean) {
        if (checked) {
            preferencesInteractor.saveThemeChecked(THEME_DARK)
        }
    }

    fun onUseDefaultThemeChange(checked: Boolean) {
        if (checked)
        preferencesInteractor.saveThemeChecked(THEME_UNDEFINED)
    }

    fun onUpdateChange(checked: Boolean) {
        checkedUpdateState.setValue(checked)
        preferencesInteractor.saveUpdateChecked(checked)
    }
}