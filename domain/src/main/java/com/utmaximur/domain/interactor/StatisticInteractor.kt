package com.utmaximur.domain.interactor

import com.utmaximur.domain.entity.DrinkStatistic
import com.utmaximur.domain.mapper.StatisticsMapper
import com.utmaximur.domain.repository.DrinkRepository
import com.utmaximur.domain.repository.TrackRepository
import javax.inject.Inject

class StatisticInteractor @Inject constructor(
    private val trackRepository: TrackRepository,
    private val drinkRepository: DrinkRepository,
    private val statisticsMapper: StatisticsMapper
) {

    suspend fun loadDrinksList(): List<DrinkStatistic> {
        return statisticsMapper.mapDrinks(
            trackRepository.getTracks(),
            drinkRepository.getDrinks()
        )
    }

    suspend fun loadStatisticsPriceByPeriod(): List<String> {
        return statisticsMapper.mapPriceListByPeriod(
            trackRepository.getTracks())
    }

    suspend fun loadStatisticsCountDays(): List<Int> {
        return statisticsMapper.mapStatisticCountDays(
            trackRepository.getTracks())
    }
}