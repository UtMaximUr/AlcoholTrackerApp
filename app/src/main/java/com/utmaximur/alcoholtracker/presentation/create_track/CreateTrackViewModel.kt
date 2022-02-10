package com.utmaximur.alcoholtracker.presentation.create_track

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.domain.interactor.AddTrackInteractor
import com.utmaximur.alcoholtracker.util.digitOnly
import com.utmaximur.alcoholtracker.util.extension.empty
import com.utmaximur.alcoholtracker.util.extension.first
import com.utmaximur.alcoholtracker.util.setPostValue
import com.utmaximur.alcoholtracker.util.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@HiltViewModel
class CreateTrackViewModel @Inject constructor(private var addTrackInteractor: AddTrackInteractor) :
    ViewModel() {

    val titleFragment: LiveData<Int> by lazy { MutableLiveData() }
    val quantityState: LiveData<Int> by lazy { MutableLiveData(Int.first()) }
    val volumeState: LiveData<Int> by lazy { MutableLiveData(Int.empty()) }
    val degreeState: LiveData<Int> by lazy { MutableLiveData(Int.empty()) }
    val eventState: LiveData<String> by lazy { MutableLiveData() }
    val valueCalculating: LiveData<String> by lazy { MutableLiveData() }
    val selectedDate: LiveData<Long> by lazy { MutableLiveData() }
    val totalMoney: LiveData<String> by lazy { MutableLiveData() }
    val drinksList: LiveData<MutableList<Drink>> by lazy { MutableLiveData() }
    val position: LiveData<Int> by lazy { MutableLiveData(Int.empty()) }
    val drinkState: LiveData<Drink> by lazy { MutableLiveData() }
    val track: LiveData<Track> by lazy { MutableLiveData() }
    val saveState: LiveData<Boolean> by lazy { MutableLiveData() }
    val createDrinkState: LiveData<Boolean> by lazy { MutableLiveData() }
    val visibleSaveButtonState: LiveData<Boolean> by lazy { MutableLiveData() }
    val visibleEditDrinkButtonState: LiveData<Boolean> by lazy { MutableLiveData() }
    val editDrinkState: LiveData<Drink> by lazy { MutableLiveData() }
    val visibleTodayState: LiveData<Boolean> by lazy { MutableLiveData() }

    private var id: String = String.empty()
    private var volume: String = String.empty()
    private var quantity: Int = Int.first()
    private var degree: String = String.empty()
    private var price: Float = Float.empty()
    private var date: Long = Date().time
    private var event: String = String.empty()
    private var drinkList: List<Drink> = ArrayList()
    private lateinit var currentDrink: Drink

    init {
        viewModelScope.launch {
            val dataDrinksList = getAllDrink()
            drinkList = dataDrinksList
            drinksList.setPostValue(dataDrinksList.toMutableList())
            initDefaultValue()
        }
    }

    private fun initDefaultValue() {
        if (id == String.empty()) {
            volume = drinkList.first().volume.first().toString()
            degree = drinkList.first().degree.first().toString()
            position.setValue(Int.empty())
        }
    }

    fun onSaveButtonClick() {
        viewModelScope.launch {
            val track = Track(
                id,
                currentDrink.drink,
                volume,
                quantity,
                degree,
                event,
                price,
                date,
                currentDrink.icon,
                currentDrink.photo
            )
            if (id == String.empty()) {
                val newTrack = track.copy(id = getTrackId())
                addTrackInteractor.insertTrack(newTrack)
            } else {
                addTrackInteractor.updateTrack(track)
            }
        }
        saveState.setValue(true)
    }

    private fun getTrackId(): String = UUID.randomUUID().toString()

    fun updateDrinks() {
        viewModelScope.launch {
            val dataDrinksList = getAllDrink()
            drinkList = dataDrinksList
            drinksList.setPostValue(dataDrinksList.toMutableList())
        }
    }

    private suspend fun getAllDrink(): List<Drink> {
        return addTrackInteractor.getDrinks()
    }

    fun onDeleteDrink() {
        viewModelScope.launch {
            addTrackInteractor.deleteDrink(currentDrink)
            updateDrinks()
        }
    }

    fun onTrackChange(track: Track?) {
        if (track != null) {
            id = track.id
            volume = track.volume
            quantity = track.quantity
            degree = track.degree
            event = track.event
            date = track.date
            price = track.price

            this.track.setValue(track)
            quantityState.setValue(track.quantity)
            eventState.setValue(track.event)
            selectedDate.setValue(track.date)
            visibleTodayState.setValue(false)
            valueCalculating.setValue(track.price.toString())
            totalMoney.setValue((track.price.times(track.quantity)).toString())

            viewModelScope.launch {
                val dataDrinksList = getAllDrink()
                dataDrinksList.forEach { drink ->
                    if (drink.drink == track.drink) {
                        currentDrink = drink
                        drinkState.setPostValue(drink)
                        position.setPostValue(dataDrinksList.indexOf(drink))
                        volumeState.setPostValue(drink.volume.indexOf(track.volume.digitOnly()))
                        degreeState.setPostValue(drink.degree.indexOf(track.degree))
                    }
                }
            }
        }
    }

    fun onTitleChange(title: Int) {
        titleFragment.setValue(title)
    }

    fun onDateChange(date: Long) {
        this.date = date
        selectedDate.setValue(date)
        visibleTodayState.setValue(false)
    }

    fun onEventChange(event: String) {
        this.event = event
    }

    fun onPriceChange(price: String) {
        if (price.isNotEmpty())
            this.price = price.toFloat()
        valueCalculating.setValue(price)
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

    fun onViewPagerPositionChange(position: Int) {
        if (position != drinkList.size) {
            currentDrink = drinkList[position]
            drinkState.setValue(currentDrink)
            volume = currentDrink.volume.first().toString()
            degree = currentDrink.degree.first().toString()
            visibleSaveButtonState.setValue(true)
            visibleEditDrinkButtonState.setValue(!currentDrink.id.isDigitsOnly())
        } else {
            visibleSaveButtonState.setValue(false)
            visibleEditDrinkButtonState.setValue(false)
        }
    }

    fun onTotalMoneyCalculating(resultCalculating: String) {
        var totalMoney = Int.empty().toString()
        if (resultCalculating.isNotEmpty()) {
            totalMoney = (quantity * resultCalculating.toFloat()).toString()
            price = resultCalculating.toFloat()
        }
        this.totalMoney.setValue(totalMoney)
        valueCalculating.setValue(resultCalculating)
    }

    fun onTotalMoneyCalculating(quantity: Int) {
        totalMoney.setValue((quantity * price.toString().toDouble()).toString())
    }

    fun onCreateClick() {
        createDrinkState.setValue(true)
    }

    fun onEditClick() {
        editDrinkState.setValue(currentDrink)
    }
}