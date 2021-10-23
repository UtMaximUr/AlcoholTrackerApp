package com.utmaximur.alcoholtracker.domain.interactor

import com.utmaximur.alcoholtracker.data.repository.DrinkRepository
import com.utmaximur.alcoholtracker.data.repository.TrackRepository
import com.utmaximur.alcoholtracker.domain.entity.DrinkStatistic
import com.utmaximur.alcoholtracker.domain.mapper.StatisticsMapper
import javax.inject.Inject

class StatisticInteractor @Inject constructor(
    private val trackRepository: TrackRepository,
    private val drinkRepository: DrinkRepository,
    private val statisticsMapper: StatisticsMapper
) {

    suspend fun loadDrinksList(): List<DrinkStatistic> {
        return statisticsMapper.mapDrinks(
            trackRepository.singleRequestTracks(),
            drinkRepository.getDrinks()
        )
    }

    suspend fun loadStatisticsPriceByPeriod(): List<String> {
        return statisticsMapper.mapPriceListByPeriod(
            trackRepository.singleRequestTracks())
    }

    suspend fun loadStatisticsCountDays(): List<Int> {
        return statisticsMapper.mapStatisticCountDays(
            trackRepository.singleRequestTracks())
    }
}