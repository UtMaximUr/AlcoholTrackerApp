package com.utmaximur.settingsManager.impl


import androidx.datastore.preferences.core.stringPreferencesKey
import com.utmaximur.settingsManager.CurrencySettingsManager
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class DefaultCurrencySettingsManager(
    private val preferencesOperation: PreferencesOperation
) : CurrencySettingsManager {

    override val currencyStateStream: Flow<String> =
        preferencesOperation.observeData(KEY_CURRENCY, KEY_CURRENCY_DEFAULT)

    override suspend fun saveCurrency(currency: String) =
        preferencesOperation.save(KEY_CURRENCY, currency)

    companion object {
        private val KEY_CURRENCY = stringPreferencesKey("KEY_CURRENCY")
        private const val KEY_CURRENCY_DEFAULT = "USD"
    }
}