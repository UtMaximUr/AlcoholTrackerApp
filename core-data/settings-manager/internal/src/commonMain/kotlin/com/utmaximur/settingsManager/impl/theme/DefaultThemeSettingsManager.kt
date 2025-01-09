package com.utmaximur.settingsManager.impl.theme

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.utmaximur.settingsManager.ThemeSettingsManager
import com.utmaximur.settingsManager.impl.PreferencesOperation
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class DefaultThemeSettingsManager(
    private val preferencesOperation: PreferencesOperation,
    systemThemeProvider: SystemThemeProvider
) : ThemeSettingsManager {

    override val darkThemeStateStream: Flow<Boolean> =
        preferencesOperation.observeData(SYSTEM_THEME_IS_DARK, systemThemeProvider.isDarkTheme)

    override suspend fun activeDarkTheme(active: Boolean) =
        preferencesOperation.save(SYSTEM_THEME_IS_DARK, active)

    companion object {
        private val SYSTEM_THEME_IS_DARK = booleanPreferencesKey("SYSTEM_THEME_IS_DARK")
    }
}