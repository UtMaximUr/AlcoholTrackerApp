package com.utmaximur.alcoholtracker.presentation.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.alcoholtracker.domain.entity.DrinkStatistic
import com.utmaximur.alcoholtracker.domain.interactor.StatisticInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatisticViewModel @Inject constructor(
    private var statisticInteractor: StatisticInteractor
) : ViewModel() {

    val statisticsDrinksList: LiveData<List<DrinkStatistic>> by lazy {
        MutableLiveData()
    }

    val statisticsPriceByPeriod: LiveData<List<String>> by lazy {
        MutableLiveData()
    }

    val statisticsCountDays: LiveData<List<Int>> by lazy {
        MutableLiveData()
    }

    fun getStatistics() = viewModelScope.launch {

        val dataDrinksList = loadDrinksList()
        (statisticsDrinksList as MutableLiveData).value = dataDrinksList

        val dataStatisticsPriceByPeriod = loadStatisticsPriceByPeriod()
        (statisticsPriceByPeriod as MutableLiveData).value = dataStatisticsPriceByPeriod

        val dataStatisticsCountDays = loadStatisticsCountDays()
        (statisticsCountDays as MutableLiveData).value = dataStatisticsCountDays
    }

    private suspend fun loadDrinksList(): List<DrinkStatistic> {
        return statisticInteractor.loadDrinksList()
    }

    private suspend fun loadStatisticsPriceByPeriod(): List<String> {
        return statisticInteractor.loadStatisticsPriceByPeriod()
    }

    private suspend fun loadStatisticsCountDays(): List<Int> {
        return statisticInteractor.loadStatisticsCountDays()
    }
}