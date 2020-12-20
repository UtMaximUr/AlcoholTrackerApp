package com.utmaximur.alcoholtracker.ui.calendar.interactor.impl

import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.`object`.AlcoholTrackerRealmObject
import com.utmaximur.alcoholtracker.data.storage.manager.StorageManager
import com.utmaximur.alcoholtracker.ui.calendar.interactor.CalendarInteractor
import io.reactivex.Flowable
import io.realm.RealmResults

class CalendarInteractorImpl(private val storageManager: StorageManager) :
    CalendarInteractor {

    override fun initRealmWithData(drinks:List<Drink>) {
        storageManager.initRealmWithData(drinks)
    }

    override fun getDrink(date: Long): AlcoholTrack? {
        return storageManager.getAlcoholTrack(date)
    }

    override fun getDrinkByMonth(month: Long): MutableList<AlcoholTrack> {
        return storageManager.getAlcoholTrackByMonth(month)
    }

    override fun deleteDrink(id: String) {
        storageManager.deleteAlcoholTrack(id)
    }
}