package com.utmaximur.alcoholtracker.presentation.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.alcoholtracker.domain.entity.DrinkStatistic
import com.utmaximur.alcoholtracker.domain.interactor.StatisticInteractor
import com.utmaximur.alcoholtracker.util.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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

    init {
        getStatistics()
    }

    private fun getStatistics() = viewModelScope.launch {

        val dataDrinksList = loadDrinksList()
        statisticsDrinksList.setValue(dataDrinksList)

        val dataStatisticsPriceByPeriod = loadStatisticsPriceByPeriod()
        statisticsPriceByPeriod.setValue(dataStatisticsPriceByPeriod)

        val dataStatisticsCountDays = loadStatisticsCountDays()
        statisticsCountDays.setValue(dataStatisticsCountDays)
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