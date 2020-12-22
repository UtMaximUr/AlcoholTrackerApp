package com.utmaximur.alcoholtracker.ui.add.addtrack.interactor

import com.utmaximur.alcoholtracker.data.model.Drink

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

    fun getAllDrink(): MutableList<Drink>

}