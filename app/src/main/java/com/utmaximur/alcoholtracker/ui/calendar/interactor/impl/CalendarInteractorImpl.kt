package com.utmaximur.alcoholtracker.ui.calendar.interactor.impl

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.manager.StorageManager
import com.utmaximur.alcoholtracker.ui.calendar.interactor.CalendarInteractor

class CalendarInteractorImpl(private val storageManager: StorageManager) :
    CalendarInteractor {

    override fun getTracks(): MutableList<AlcoholTrack> {
        return storageManager.getAllAlcoholTrack()
    }

    override fun deleteDrink(alcoholTrack: AlcoholTrack) {
        storageManager.deleteAlcoholTrack(alcoholTrack)
    }

    override fun getDrink(date: Long): AlcoholTrack? {
        return storageManager.getTrack(date)
    }
}