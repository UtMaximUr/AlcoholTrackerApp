package com.utmaximur.alcoholtracker.ui.statistic.interactor.impl

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.manager.StorageManager
import com.utmaximur.alcoholtracker.ui.statistic.interactor.StatisticInteractor

class StatisticInteractorImpl(private val storageManager: StorageManager) :
    StatisticInteractor {

    override fun getAllDrink(): MutableList<Drink> {
        return storageManager.getAllDrink()
    }

    override fun getAllAlcoholTrack(): MutableList<AlcoholTrack> {
        return storageManager.getAllAlcoholTrack()
    }

}