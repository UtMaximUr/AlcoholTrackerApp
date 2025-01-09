package com.utmaximur.domain.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val appVersionStream: Flow<String>

    val darkThemeStateStream: Flow<Boolean>

    val currencyStateStream: Flow<String>

    suspend fun activeDarkTheme(active: Boolean)

    suspend fun saveCurrency(currency: String)

}