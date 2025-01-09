package com.utmaximur.settingsManager

import kotlinx.coroutines.flow.Flow

interface ThemeSettingsManager {

    val darkThemeStateStream: Flow<Boolean>

    suspend fun activeDarkTheme(active: Boolean)
}