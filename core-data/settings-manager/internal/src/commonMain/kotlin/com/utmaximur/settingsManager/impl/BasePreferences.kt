package com.utmaximur.settingsManager.impl

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.utmaximur.app.base.coroutines.NamedCoroutineDispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

internal interface PreferencesOperation {
    fun <T> observeData(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun <T> read(key: Preferences.Key<T>, defaultValue: T): T
    suspend fun <T> save(key: Preferences.Key<T>, value: T)
    suspend fun <T> clear(key: Preferences.Key<T>)
}

@Factory
internal class BasePreferencesReader(
    private val dataStore: DataStore<Preferences>,
    @NamedCoroutineDispatcherIO private val ioDispatcher: CoroutineDispatcher
) : PreferencesOperation {

    override fun <T> observeData(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException)
                    emptyPreferences()
                else
                    throw exception
            }
            .map { preferences ->
                // No type safety operation.
                preferences[key] ?: defaultValue
            }

    override suspend fun <T> read(key: Preferences.Key<T>, defaultValue: T): T = withContext(ioDispatcher) {
        observeData(key, defaultValue).first()
    }

    override suspend fun <T> save(key: Preferences.Key<T>, value: T): Unit = withContext(ioDispatcher) {
        dataStore.edit { settings ->
            settings[key] = value
        }
    }

    override suspend fun <T> clear(key: Preferences.Key<T>) {
        dataStore.edit { settings ->
            settings.remove(key)
        }
    }
}