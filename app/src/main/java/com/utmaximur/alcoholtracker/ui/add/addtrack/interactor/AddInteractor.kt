package com.utmaximur.alcoholtracker.ui.add.addtrack.interactor

import androidx.lifecycle.LiveData
import com.utmaximur.alcoholtracker.data.model.Drink
import kotlin.collections.HashMap

interface AddInteractor {

    fun save(
        id: String,
        drink: String,
        volume: String,
        quantity: Int,
        degree: String,
        price: Float,
        date: Long,
        icon: Int
    )

    fun getLiveData(): LiveData<HashMap<String, Long>>

    fun getAllDrink(): MutableList<Drink>

}