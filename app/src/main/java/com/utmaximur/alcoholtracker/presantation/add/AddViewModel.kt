package com.utmaximur.alcoholtracker.presantation.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.domain.interactor.AddTrackInteractor
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class AddViewModel @Inject constructor(private var addTrackInteractor: AddTrackInteractor) :
    ViewModel() {

    var id: String = ""
    var drink: String = ""
    var volume: String = ""
    val quantity: Int = 0
    var degree: String = ""
    var price: Float = 0.0f
    var date: Long = Date().time
    var icon: Int = 0
    var drinkDBOS: List<Drink> = ArrayList()
    var volumes: List<String?> = ArrayList()
    var degrees: List<String?> = ArrayList()

    val drinksList: LiveData<List<Drink>> by lazy {
        MutableLiveData()
    }

    init {
        viewModelScope.launch {

            val dataDrinksList = getAllDrink()
            (drinksList as MutableLiveData).value = dataDrinksList

        }
    }

    fun onSaveButtonClick(track: Track) {
        if (track.id == "") {
//            track.id = getTrackId()
            viewModelScope.launch {
                addTrackInteractor.insertTrack(track)
            }
        } else {
            viewModelScope.launch {
                addTrackInteractor.updateTrack(track)
            }
        }
    }

    private fun getTrackId(): String = UUID.randomUUID().toString()

    fun checkIsEmptyFieldPrice(price: Float): Boolean {
        return price != 0.0f
    }

    fun getTotalMoney(quantity: Int, price: Float): String {
        return (quantity * price.toString().toDouble()).toString()
    }

    private suspend fun getAllDrink(): List<Drink> {
        return addTrackInteractor.getDrinks()
    }

    suspend fun deleteDrink(drink: Drink) {
        addTrackInteractor.deleteDrink(drink)
    }
}