package com.utmaximur.alcoholtracker.data.storage.manager.impl

import androidx.lifecycle.LiveData
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.`object`.AlcoholTrackerRealmObject
import com.utmaximur.alcoholtracker.data.storage.manager.StorageManager
import com.utmaximur.alcoholtracker.data.storage.module.StorageModule
import io.reactivex.Flowable
import io.realm.RealmResults


class StorageManagerImpl(private val storageModule: StorageModule): StorageManager {

    override fun getPaymentsLiveData(): LiveData<Map<String, AlcoholTrack>> {
        return storageModule.getPaymentsLiveData()
    }

    override fun initRealmWithData(drinks:List<Drink>) {
        storageModule.initRealmWithData(drinks)
    }

    override fun deleteAlcoholTrack(id: String) {
        storageModule.deleteAlcoholTrack(id)
    }

    override fun addAlcoholTrack(alcoholTrack: AlcoholTrack) {
        storageModule.addAlcoholTrack(alcoholTrack)
    }

    override fun getAlcoholTrack(date: Long): AlcoholTrack? {
        return storageModule.getAlcoholTrack(date)
    }

    override fun getAlcoholTrackByMonth(month: Long): MutableList<AlcoholTrack> {
        return storageModule.getAlcoholTrackByMonth(month)
    }

    override fun addDrink(drink: Drink) {
        storageModule.addDrink(drink)
    }

    override fun getAllDrink(): MutableList<Drink> {
        return storageModule.getAllDrink()
    }

    override fun getAllAlcoholTrack(): MutableList<AlcoholTrack> {
        return storageModule.getAllAlcoholTrack()
    }
}