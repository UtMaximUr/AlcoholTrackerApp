package com.utmaximur.alcoholtracker.data.storage.manager

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink

interface StorageManager {

    fun addAlcoholTrack(alcoholTrack: AlcoholTrack)

    fun deleteAlcoholTrack(alcoholTrack: AlcoholTrack)

    fun getAllDrink(): MutableList<Drink>

    fun getAllAlcoholTrack(): MutableList<AlcoholTrack>

    fun getTrack(date: Long): AlcoholTrack?

}