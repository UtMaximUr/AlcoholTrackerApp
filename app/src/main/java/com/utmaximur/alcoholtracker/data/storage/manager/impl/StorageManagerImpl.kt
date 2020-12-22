package com.utmaximur.alcoholtracker.data.storage.manager.impl

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.manager.StorageManager
import com.utmaximur.alcoholtracker.data.storage.module.StorageModule

class StorageManagerImpl(private val storageModule: StorageModule) : StorageManager {

    override fun deleteAlcoholTrack(alcoholTrack: AlcoholTrack) {
        storageModule.deleteAlcoholTrack(alcoholTrack)
    }

    override fun addAlcoholTrack(alcoholTrack: AlcoholTrack) {
        storageModule.addAlcoholTrack(alcoholTrack)
    }

    override fun getAllDrink(): MutableList<Drink> {
        return storageModule.getAllDrink()
    }

    override fun getAllAlcoholTrack(): MutableList<AlcoholTrack> {
        return storageModule.getAllAlcoholTrack()
    }

    override fun getTrack(date: Long): AlcoholTrack? {
        return storageModule.getTrack(date)
    }
}