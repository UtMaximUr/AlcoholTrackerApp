package com.utmaximur.alcoholtracker.ui.add.addtrack.interactor.impl

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.manager.StorageManager
import com.utmaximur.alcoholtracker.ui.add.addtrack.interactor.AddInteractor

class AddInteractorImpl(private val storageManager: StorageManager) :
    AddInteractor {

    override fun save(
        id: String,
        drink: String,
        volume: String,
        quantity: Int,
        degree: String,
        price: Float,
        date: Long,
        icon: Int
    ) {

        val drinkEntity = AlcoholTrack(
            id = id,
            drink = drink,
            volume = volume,
            quantity = quantity,
            degree = degree,
            price = price,
            date = date,
            icon = icon

        )

        storageManager.addAlcoholTrack(drinkEntity)
    }

    override fun getAllDrink(): MutableList<Drink>{
        return storageManager.getAllDrink()
    }
}