package com.utmaximur.settingsManager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

internal fun createDataStore(produceFile: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { produceFile().toPath() },
    )

internal const val dataStoreFileName = "alcoholTrackerApp.preferences_pb"
