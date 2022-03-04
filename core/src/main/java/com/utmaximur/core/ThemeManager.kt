package com.utmaximur.core

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.utmaximur.domain.interactor.PreferencesInteractor
import com.utmaximur.utils.THEME_DARK
import com.utmaximur.utils.THEME_LIGHT
import javax.inject.Inject

class ThemeManager @Inject constructor(private val preferencesInteractor: PreferencesInteractor) {

    @Composable
    fun themeApp(): Boolean {
        return when (preferencesInteractor.getSaveTheme()) {
            THEME_DARK -> true
            THEME_LIGHT -> false
            else -> isSystemInDarkTheme()
        }
    }
}