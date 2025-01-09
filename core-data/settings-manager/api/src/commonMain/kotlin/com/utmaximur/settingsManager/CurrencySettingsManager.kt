package com.utmaximur.settingsManager

import kotlinx.coroutines.flow.Flow

interface CurrencySettingsManager {

    val currencyStateStream: Flow<String>

    suspend fun saveCurrency(currency: String)
}