package com.utmaximur.alcoholtracker.presentation.create_track

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.domain.interactor.AddTrackInteractor
import com.utmaximur.alcoholtracker.util.extension.empty
import com.utmaximur.alcoholtracker.util.extension.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CreateTrackViewModel @Inject constructor(private var addTrackInteractor: AddTrackInteractor) :
    ViewModel() {

    private var id: String = String.empty()
    private var drink: String = String.empty()
    private var volume: String = String.empty()
    private var quantity: Int = Int.first()
    private var degree: String = String.empty()
    private var price: Float = Float.empty()
    private var date: Long = Date().time
    private var icon: String = String.empty()
    private var event: String = String.empty()
    private var image: String = String.empty()
    private var drinkList: List<Drink> = ArrayList()
    private var degreeList: List<String?> = ArrayList()

    val totalMoney: LiveData<String> by lazy {
        MutableLiveData()
    }

    val track: LiveData<Track> by lazy {
        MutableLiveData()
    }

    val drinksList: LiveData<List<Drink>> by lazy {
        MutableLiveData()
    }

    val valueCalculating: LiveData<String> by lazy {
        MutableLiveData()
    }

    init {
        viewModelScope.launch {
            val dataDrinksList = getAllDrink()
            drinkList = dataDrinksList
            (drinksList as MutableLiveData).value = dataDrinksList
            initDefaultValue()
        }
    }

    private fun initDefaultValue() {
        if (id == String.empty()) {
            drink = drinkList.first().drink
            volume = drinkList.first().volume.first().toString()
            degree = drinkList.first().degree.first().toString()
            icon = drinkList.first().icon
            image = drinkList.first().photo
        }
    }

    fun onSaveButtonClick() {
        viewModelScope.launch {
            val track = Track(
                id,
                drink,
                volume,
                quantity,
                degree,
                event,
                price,
                date,
                icon,
                image
            )
            if (id == String.empty()) {
                val newTrack = track.copy(id = getTrackId())
                addTrackInteractor.insertTrack(newTrack)
            } else {
                addTrackInteractor.updateTrack(track)
            }
        }
    }

    private fun getTrackId(): String = UUID.randomUUID().toString()

    fun checkIsEmptyFieldPrice(price: Float): Boolean {
        return price != 0.0f
    }

    fun getTotalMoney(quantity: Int, price: Float) {
        (totalMoney as MutableLiveData).value = (quantity * price.toString().toDouble()).toString()
    }

    private suspend fun getAllDrink(): List<Drink> {
        return addTrackInteractor.getDrinks()
    }

    suspend fun onDeleteDrink(drink: Drink) {
        addTrackInteractor.deleteDrink(drink)
    }

    fun onTrackChange(track: Track?) {
        (this.track as MutableLiveData).value = track

        id = track?.id!!
        drink = track.drink
        volume = track.volume
        quantity = track.quantity
        degree = track.degree
        event = track.event
        date = track.date
        price = track.price
        icon = track.icon
        image = track.image

        (totalMoney as MutableLiveData).value = (track.price.times(track.quantity)).toString()
    }

    fun onDateChange(date: Long) {
        this.date = date
    }

    fun onEventChange(event: String) {
        this.event = event
    }

    fun onPriceChange(price: Float) {
        this.price = price
    }

    fun onDegreeChange(degree: String) {
        this.degree = degree
    }

    fun onQuantityChange(quantity: Int) {
        this.quantity = quantity
    }

    fun onVolumeChange(volume: String) {
        this.volume = volume
    }

    fun getPrice(): Float = price

    fun getQuantity(): Int = quantity

    fun getDate(): Long = date

    fun setDegreeList(degreeList: List<String?>) {
        this.degreeList = degreeList
    }

    fun getDrinkList(): List<Drink> = drinkList

    fun onViewPagerPositionChange(position: Int) {
        icon = drinkList[position].icon
        drink = drinkList[position].drink
        volume = drinkList[position].volume.first()!!
        degree = drinkList[position].degree.first()!!
        image = drinkList[position].photo
    }

    fun onValueCalculating(resultCalculating: String) {
        var totalMoney = Int.empty().toString()
        if (resultCalculating.isNotEmpty()) {
            totalMoney = (quantity * resultCalculating.toFloat()).toString()
        }
        (this.totalMoney as MutableLiveData).value = totalMoney
        (valueCalculating as MutableLiveData).value = resultCalculating
    }
}