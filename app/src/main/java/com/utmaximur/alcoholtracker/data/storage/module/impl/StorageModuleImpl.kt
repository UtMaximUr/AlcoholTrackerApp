package com.utmaximur.alcoholtracker.data.storage.module.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.utmaximur.alcoholtracker.data.extension.conversion.toAlcoholTrack
import com.utmaximur.alcoholtracker.data.extension.conversion.toDrink
import com.utmaximur.alcoholtracker.data.extension.conversion.toRealmObject
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.storage.`object`.AlcoholTrackerRealmObject
import com.utmaximur.alcoholtracker.data.storage.`object`.DrinkRealmObject
import com.utmaximur.alcoholtracker.data.storage.module.StorageModule
import com.utmaximur.alcoholtracker.data.storage.service.StorageService
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import java.util.*
import kotlin.collections.ArrayList
import io.reactivex.Flowable

class StorageModuleImpl(storageService: StorageService) : StorageModule {

    private val realm: Realm = storageService.realm

    private val paymentsLiveData: MutableLiveData<Map<String, AlcoholTrack>> =
        MutableLiveData()

    override fun getPaymentsLiveData(): LiveData<Map<String, AlcoholTrack>> {
        return paymentsLiveData
    }

    override fun initRealmWithData(drinks:List<Drink>) {
        drinks.forEach {
            addDrink(it)
        }
    }

    override fun addDrink(drink: Drink) {
        realm.executeTransaction { realm ->
            realm.insertOrUpdate(drink.toRealmObject())
        }
    }

    override fun getAllDrink(): MutableList<Drink> {
        val drinkObject = realm.where<DrinkRealmObject>()
            .findAll()
        val drink: ArrayList<Drink> = ArrayList()
        for (i in 0 until drinkObject.size) {
            drink.add(drinkObject[i]!!.toDrink()!!)
        }
        return drink
    }

    override fun getAllAlcoholTrack(): MutableList<AlcoholTrack> {
        val trackObject = realm.where<AlcoholTrackerRealmObject>()
            .findAll()
        val alcoholTrack: ArrayList<AlcoholTrack> = ArrayList()
        for (i in 0 until trackObject.size) {
            alcoholTrack.add(trackObject[i]!!.toAlcoholTrack()!!)
        }
        return alcoholTrack
    }

    override fun deleteAlcoholTrack(id: String) {
        val rent = realm.where<AlcoholTrackerRealmObject>()
            .equalTo("id", id)
            .findFirst()

        realm.executeTransaction {
            rent?.deleteFromRealm()
        }
    }

    override fun addAlcoholTrack(alcoholTrack: AlcoholTrack) {

        Log.d(
            "====", "StorageModuleImpl -> addDrink -> дата: " + alcoholTrack.date
                    + " градус: " + alcoholTrack.degree
                    + " напиток: " + alcoholTrack.drink
                    + " цена: " + alcoholTrack.price
                    + " количество: " + alcoholTrack.quantity
                    + " объем: " + alcoholTrack.volume
                    + " id: " + alcoholTrack.id
                    + " icon: " + alcoholTrack.icon
        )
        var id = alcoholTrack.id
        if (alcoholTrack.id == "") {
            id = getId()
        }

        realm.executeTransaction { realm ->
            alcoholTrack.id = id
            realm.copyToRealmOrUpdate(alcoholTrack.toRealmObject())
        }
    }

    override fun getAlcoholTrack(date: Long): AlcoholTrack? {
        val drinkObject = realm.where<AlcoholTrackerRealmObject>()
            .equalTo("date", date)
            .findFirst()

        return drinkObject?.toAlcoholTrack()
    }

    override fun getAlcoholTrackByMonth(month: Long): MutableList<AlcoholTrack> {
        val drinkObject = realm.where<AlcoholTrackerRealmObject>()
            .findAll()
        val alcoholTracks: ArrayList<AlcoholTrack> = ArrayList()
        for (i in 0 until drinkObject.size) {
            alcoholTracks.add(drinkObject[i]!!.toAlcoholTrack()!!)
        }
        return alcoholTracks
    }

    private fun getId(): String = UUID.randomUUID().toString()

}