package com.utmaximur.alcoholtracker.data.storage.module.impl

import android.util.Log
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.module.StorageModule
import com.utmaximur.alcoholtracker.data.storage.service.StorageService

class StorageModuleImpl(private val storageService: StorageService) : StorageModule {

    override fun updateDrink(drink: Drink) {
        storageService.drinkDao().addDrink(drink)
    }

    override fun getAllDrink(): MutableList<Drink> {
        return storageService.drinkDao().getDrinks()
    }

    override fun addAlcoholTrack(track: AlcoholTrack) {
        storageService.trackDao().insertTrack(track)
    }

    override fun updateTrack(track: AlcoholTrack) {
        storageService.trackDao().updateTrack(track)
    }

    override fun deleteAlcoholTrack(track: AlcoholTrack) {
        storageService.trackDao().deleteTrack(track)
    }

    override fun getAllAlcoholTrack(): MutableList<AlcoholTrack> {
        return storageService.trackDao().getTracks()
    }

    override fun getTrack(date: Long): AlcoholTrack? {
        return storageService.trackDao().getTrack(date)
    }
}