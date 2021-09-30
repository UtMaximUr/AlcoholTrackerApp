package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.domain.interactor.StatisticInteractor
import com.utmaximur.alcoholtracker.presantation.statistic.StatisticViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatisticViewModelFactory @Inject constructor(
    private var statisticInteractor: StatisticInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StatisticViewModel(statisticInteractor) as T
    }
}