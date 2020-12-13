package com.utmaximur.alcoholtracker.ui.add.addtrack.interactor.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
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

    override fun getLiveData(): LiveData<HashMap<String, Long>> {


        val dateLiveData =
            Transformations.map(storageManager.getPaymentsLiveData()) { map: Map<String, AlcoholTrack> ->
                val newMap = hashMapOf<String, Long>()
                newMap
            }

        return dateLiveData

    }
}