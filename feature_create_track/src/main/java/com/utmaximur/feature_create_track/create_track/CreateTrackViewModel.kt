package com.utmaximur.feature_create_track.create_track

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.utils.*
import com.utmaximur.utils.zero
import com.utmaximur.utils.first
import com.utmaximur.domain.entity.Drink
import com.utmaximur.domain.entity.Track
import com.utmaximur.domain.interactor.AddTrackInteractor
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
    val volumeState: LiveData<Int> by lazy { MutableLiveData(Int.zero()) }
    val degreeState: LiveData<Int> by lazy { MutableLiveData(Int.zero()) }
    val eventState: LiveData<String> by lazy { MutableLiveData() }
    val valueCalculating: LiveData<String> by lazy { MutableLiveData() }
    val selectedDate: LiveData<Long> by lazy { MutableLiveData() }
    val totalMoney: LiveData<String> by lazy { MutableLiveData() }
    val drinksList: LiveData<MutableList<Drink>> by lazy { MutableLiveData() }
    val position: LiveData<Int> by lazy { MutableLiveData(Int.zero()) }
    val drinkState: LiveData<Drink> by lazy { MutableLiveData() }
    val visibleSaveButtonState: LiveData<Boolean> by lazy { MutableLiveData() }
    val visibleEditDrinkButtonState: LiveData<Boolean> by lazy { MutableLiveData() }
    val visibleTodayState: LiveData<Boolean> by lazy { MutableLiveData() }
    val drink: LiveData<Drink> by lazy { MutableLiveData() }

    private var id: String = String.empty()
    private var volume: String = String.empty()
    private var quantity: Int = Int.first()
    private var degree: String = String.empty()
    private var price: Float = Float.zero()
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
            position.setValue(Int.zero())
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

    fun onTrackChange(editTrackId: String, title: Int) {

        titleFragment.setValue(title)

        viewModelScope.launch {
            val editTrack = addTrackInteractor.getTrackById(editTrackId)

            id = editTrack.id
            volume = editTrack.volume
            quantity = editTrack.quantity
            degree = editTrack.degree
            event = editTrack.event
            date = editTrack.date
            price = editTrack.price

            quantityState.setValue(editTrack.quantity)
            eventState.setValue(editTrack.event)
            selectedDate.setValue(editTrack.date)
            visibleTodayState.setValue(false)
            valueCalculating.setValue(editTrack.price.toString())
            totalMoney.setValue((editTrack.price.times(editTrack.quantity)).toString())

            val dataDrinksList = getAllDrink()
            dataDrinksList.forEach { drink ->
                if (drink.drink == editTrack.drink) {
                    currentDrink = drink
                    drinkState.setPostValue(drink)
                    position.setPostValue(dataDrinksList.indexOf(drink))
                    volumeState.setPostValue(drink.volume.indexOf(editTrack.volume.digitOnly()))
                    degreeState.setPostValue(drink.degree.indexOf(editTrack.degree))
                }
            }
        }
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

    fun onDegreeChange(degree: String, position: Int) {
        this.degree = degree
        degreeState.setValue(position)
    }

    fun onQuantityChange(quantity: Int) {
        this.quantity = quantity
        onTotalMoneyCalculating(quantity)
        quantityState.setValue(quantity)
    }

    fun onVolumeChange(volume: String, position: Int) {
        this.volume = volume
        volumeState.setValue(position)
    }

    fun onViewPagerPositionChange(position: Int) {
        if (position != drinkList.size) {
            currentDrink = drinkList[position]
            drink.setValue(currentDrink)
            drinkState.setValue(currentDrink)
            volume = currentDrink.volume.first().toString()
            degree = currentDrink.degree.first().toString()
            volumeState.setValue(Int.zero())
            degreeState.setValue(Int.zero())
            visibleSaveButtonState.setValue(true)
            visibleEditDrinkButtonState.setValue(!currentDrink.id.isDigitsOnly())
        } else {
            visibleSaveButtonState.setValue(false)
            visibleEditDrinkButtonState.setValue(false)
        }
    }

    fun onTotalMoneyCalculating(resultCalculating: String) {
        var totalMoney = Int.zero().toString()
        if (resultCalculating.isNotEmpty()) {
            totalMoney = (quantity * resultCalculating.toFloat()).toString()
            price = resultCalculating.toFloat()
        }
        this.totalMoney.setValue(totalMoney)
        valueCalculating.setValue(resultCalculating)
    }

    private fun onTotalMoneyCalculating(quantity: Int) {
        totalMoney.setValue((quantity * price.toString().toDouble()).toString())
    }

    fun isDarkTheme(isDark: Boolean): Boolean {
        return when (addTrackInteractor.getSaveTheme()) {
            THEME_DARK -> true
            THEME_LIGHT -> false
            else -> isDark
        }
    }
}