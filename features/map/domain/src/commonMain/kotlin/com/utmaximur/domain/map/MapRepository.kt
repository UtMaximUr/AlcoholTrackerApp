package com.utmaximur.domain.map

import kotlinx.coroutines.flow.Flow

interface MapRepository {

    val darkThemeStateStream: Flow<Boolean>

    fun observePlace(): Flow<List<PlaceMark>>
}
