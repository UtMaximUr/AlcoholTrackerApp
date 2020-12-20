package com.utmaximur.alcoholtracker.data.storage.manager

import androidx.lifecycle.LiveData
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.`object`.AlcoholTrackerRealmObject
import io.reactivex.Flowable
import io.realm.RealmResults

interface StorageManager {

    fun getPaymentsLiveData(): LiveData<Map<String, AlcoholTrack>>

    fun initRealmWithData(drinks:List<Drink>)

    fun addAlcoholTrack(alcoholTrack: AlcoholTrack)

    fun getAlcoholTrack(date: Long): AlcoholTrack?

    fun getAlcoholTrackByMonth(month: Long): MutableList<AlcoholTrack>

    fun deleteAlcoholTrack(id: String)

    fun addDrink(drink: Drink)

    fun getAllDrink(): MutableList<Drink>

    fun getAllAlcoholTrack(): MutableList<AlcoholTrack>

}