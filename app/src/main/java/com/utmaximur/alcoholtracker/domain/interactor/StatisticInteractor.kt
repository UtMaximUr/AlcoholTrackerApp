package com.utmaximur.alcoholtracker.domain.interactor

import com.utmaximur.alcoholtracker.data.mapper.TrackMapper
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
            trackRepository.getTracksList().map { TrackMapper().map(it) },
            drinkRepository.getDrinksList()
        )
    }

    suspend fun loadStatisticsPriceByPeriod(): List<String> {
        return statisticsMapper.mapPriceListByPeriod(
            trackRepository.getTracksList().map { TrackMapper().map(it) })
    }

    suspend fun loadStatisticsCountDays(): List<Int> {
        return statisticsMapper.mapStatisticCountDays(
            trackRepository.getTracksList().map { TrackMapper().map(it) })
    }
}