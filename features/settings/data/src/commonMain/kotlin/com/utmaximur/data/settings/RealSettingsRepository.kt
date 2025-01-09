package com.utmaximur.data.settings

import com.utmaximur.app.base.app.ApplicationInfo
import com.utmaximur.domain.settings.SettingsRepository
import com.utmaximur.settingsManager.CurrencySettingsManager
import com.utmaximur.settingsManager.ThemeSettingsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Factory

@Factory
internal class RealSettingsRepository(
    applicationInfo: ApplicationInfo,
    private val themeSettingsManager: ThemeSettingsManager,
    private val currencySettingsManager: CurrencySettingsManager
) : SettingsRepository {

    override val appVersionStream: Flow<String> =
        flow { emit(applicationInfo.versionName) }

    override val darkThemeStateStream: Flow<Boolean> =
        themeSettingsManager.darkThemeStateStream

    override val currencyStateStream: Flow<String> =
        currencySettingsManager.currencyStateStream

    override suspend fun activeDarkTheme(active: Boolean) =
        themeSettingsManager.activeDarkTheme(active)

    override suspend fun saveCurrency(currency: String) =
        currencySettingsManager.saveCurrency(currency)
}