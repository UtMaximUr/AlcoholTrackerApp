package com.utmaximur.core

import com.utmaximur.domain.interactor.PreferencesInteractor
import com.utmaximur.utils.THEME_DARK
import com.utmaximur.utils.THEME_LIGHT
import javax.inject.Inject

class ThemeManager @Inject constructor(private val preferencesInteractor: PreferencesInteractor) {

    fun themeApp(): Boolean? {
        return when (preferencesInteractor.getSaveTheme()) {
            THEME_DARK -> true
            THEME_LIGHT -> false
            else -> null
        }
    }
}