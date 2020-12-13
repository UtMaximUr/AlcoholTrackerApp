package com.utmaximur.alcoholtracker.ui.statistic.interactor

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink

interface StatisticInteractor {

    fun getAllDrink(): MutableList<Drink>

    fun getAllAlcoholTrack(): MutableList<AlcoholTrack>

}