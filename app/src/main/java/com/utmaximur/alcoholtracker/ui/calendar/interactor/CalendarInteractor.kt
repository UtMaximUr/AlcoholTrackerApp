package com.utmaximur.alcoholtracker.ui.calendar.interactor

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink

interface CalendarInteractor {

    fun getTracks(): MutableList<AlcoholTrack>

    fun deleteDrink(alcoholTrack: AlcoholTrack)

    fun getDrink(date: Long): AlcoholTrack?

}