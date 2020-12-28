package com.utmaximur.alcoholtracker.ui.statistic.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.repository.TrackRepository

class StatisticViewModel(private var drinkRepository: DrinkRepository,
                         private var trackRepository: TrackRepository
): ViewModel() {

    fun getAllDrink(): LiveData<MutableList<Drink>> {
        return  drinkRepository.getDrinks()
    }

    fun getAllTrack(): LiveData<MutableList<AlcoholTrack>> {
        return  trackRepository.getTracks()
    }
}