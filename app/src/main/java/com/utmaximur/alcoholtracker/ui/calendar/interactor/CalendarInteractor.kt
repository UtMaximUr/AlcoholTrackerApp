package com.utmaximur.alcoholtracker.ui.calendar.interactor

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.`object`.AlcoholTrackerRealmObject
import io.reactivex.Flowable
import io.realm.RealmResults

interface CalendarInteractor {

    fun initRealmWithData(drinks:List<Drink>)

    fun getDrink(date: Long): AlcoholTrack?

    fun getDrinkByMonth(month: Long): MutableList<AlcoholTrack>

    fun deleteDrink(id: String)

}