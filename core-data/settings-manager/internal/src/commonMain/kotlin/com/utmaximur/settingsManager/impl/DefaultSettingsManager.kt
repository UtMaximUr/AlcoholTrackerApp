package com.utmaximur.settingsManager.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import org.koin.core.annotation.Factory
import com.utmaximur.settingsManager.SettingsManager

@Factory
internal class DefaultSettingsManager(
    private val dataStore: DataStore<Preferences>
) : SettingsManager {
    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }
}


