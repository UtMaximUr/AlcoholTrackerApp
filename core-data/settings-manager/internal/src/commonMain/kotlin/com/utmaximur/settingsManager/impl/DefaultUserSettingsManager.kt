package com.utmaximur.settingsManager.impl

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.utmaximur.settingsManager.UserSettingsManager
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
internal class DefaultUserSettingsManager(
    private val preferencesOperation: PreferencesOperation
) : UserSettingsManager {

    override val heightStateStream: Flow<Int> =
        preferencesOperation.observeData(KEY_HEIGHT, VALUE_HEIGHT_DEFAULT)

    override val weightStateStream: Flow<Int> =
        preferencesOperation.observeData(KEY_WEIGHT, VALUE_WEIGHT_DEFAULT)

    override val genderStateStream: Flow<String> =
        preferencesOperation.observeData(KEY_GENDER, VALUE_GENDER_DEFAULT)

    override suspend fun saveHeight(height: Int) =
        preferencesOperation.save(KEY_HEIGHT, height)

    override suspend fun saveWeight(weight: Int) =
        preferencesOperation.save(KEY_WEIGHT, weight)

    override suspend fun saveGender(gender: String) =
        preferencesOperation.save(KEY_GENDER, gender)

    companion object {
        private val KEY_HEIGHT = intPreferencesKey("KEY_HEIGHT")
        private val KEY_WEIGHT = intPreferencesKey("KEY_WEIGHT")
        private val KEY_GENDER = stringPreferencesKey("KEY_GENDER")
        private const val VALUE_HEIGHT_DEFAULT = 0
        private const val VALUE_WEIGHT_DEFAULT = 0
        private const val VALUE_GENDER_DEFAULT = ""
    }
}