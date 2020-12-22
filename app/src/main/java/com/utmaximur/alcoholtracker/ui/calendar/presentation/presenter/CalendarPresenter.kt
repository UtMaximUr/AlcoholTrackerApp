package com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.ui.base.MvpPresenter
import com.utmaximur.alcoholtracker.ui.calendar.presentation.view.CalendarView

interface CalendarPresenter : MvpPresenter<CalendarView> {

    fun getTracks(): MutableList<AlcoholTrack>

    fun getDrink(date: Long): AlcoholTrack?

    fun deleteDrink(alcoholTrack: AlcoholTrack)

    fun getAlcoholTrackByDay(eventDay: Long): MutableList<AlcoholTrack>

    fun setIconOnDate()

}