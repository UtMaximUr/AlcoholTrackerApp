package com.utmaximur.settingsManager

import kotlinx.coroutines.flow.Flow

interface UserSettingsManager {

    val heightStateStream: Flow<Int>
    val weightStateStream: Flow<Int>
    val genderStateStream: Flow<String>

    suspend fun saveHeight(height: Int)

    suspend fun saveWeight(weight: Int)

    suspend fun saveGender(gender: String)
}