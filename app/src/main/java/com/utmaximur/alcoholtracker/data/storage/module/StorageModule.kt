package com.utmaximur.alcoholtracker.data.storage.module

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink

interface StorageModule {

    fun updateDrink(drink: Drink)

    fun getAllDrink(): MutableList<Drink>

    fun addAlcoholTrack(track: AlcoholTrack)

    fun updateTrack(track: AlcoholTrack)

    fun deleteAlcoholTrack(track: AlcoholTrack)

    fun getAllAlcoholTrack(): MutableList<AlcoholTrack>

    fun getTrack(date: Long): AlcoholTrack?

}