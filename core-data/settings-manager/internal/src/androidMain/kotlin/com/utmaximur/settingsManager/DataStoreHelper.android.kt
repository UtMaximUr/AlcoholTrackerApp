package com.utmaximur.settingsManager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.annotation.Single

@Single
fun provideDataStore(context: Context): DataStore<Preferences> =
    createDataStore(produceFile = { context.filesDir.resolve(dataStoreFileName).absolutePath })