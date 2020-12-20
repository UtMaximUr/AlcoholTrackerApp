package com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter

import android.content.Context
import com.applandeo.materialcalendarview.EventDay
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.storage.`object`.AlcoholTrackerRealmObject
import com.utmaximur.alcoholtracker.ui.base.MvpPresenter
import com.utmaximur.alcoholtracker.ui.calendar.presentation.view.CalendarView
import io.reactivex.Flowable
import io.realm.RealmResults

interface CalendarPresenter : MvpPresenter<CalendarView> {

    fun getDrink(date: Long): AlcoholTrack?

    fun getDrinkByMonth(month: Long): MutableList<AlcoholTrack>

    fun deleteDrink(id: String)

    fun initRealmWithData(context: Context)

    fun getAlcoholTrackByDay(eventDay: Long): MutableList<AlcoholTrack>

    fun setIconOnDate()

}