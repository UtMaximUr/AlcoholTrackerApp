package com.utmaximur.domain.statistic

import com.utmaximur.domain.Drink
import com.utmaximur.domain.Track
import kotlinx.coroutines.flow.Flow

interface StatisticRepository {
    val currencyStream: Flow<String>
    val tracksStream: Flow<List<Track>>
    val drinksStream: Flow<List<Drink>>
}
