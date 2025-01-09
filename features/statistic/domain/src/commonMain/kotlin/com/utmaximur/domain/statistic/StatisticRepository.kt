package com.utmaximur.domain.statistic

import com.utmaximur.domain.models.Drink
import com.utmaximur.domain.models.Track
import kotlinx.coroutines.flow.Flow


interface StatisticRepository {
    val currencyStream: Flow<String>
    val tracksStream: Flow<List<Track>>
    val drinksStream: Flow<List<Drink>>
}