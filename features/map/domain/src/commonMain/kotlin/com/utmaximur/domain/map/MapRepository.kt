package com.utmaximur.domain.map

import com.utmaximur.domain.models.Place
import kotlinx.coroutines.flow.Flow

interface MapRepository {

    val darkThemeStateStream: Flow<Boolean>

    fun observePlace(): Flow<List<Place>>
}
