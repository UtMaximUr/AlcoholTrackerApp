package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.repository.TrackRepository
import com.utmaximur.alcoholtracker.ui.statistic.StatisticViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatisticViewModelFactory  @Inject constructor(private var drinkRepository: DrinkRepository,
                                                     private var trackRepository: TrackRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StatisticViewModel(drinkRepository, trackRepository)  as T
    }
}