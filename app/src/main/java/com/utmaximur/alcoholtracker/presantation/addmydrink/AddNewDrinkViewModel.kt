package com.utmaximur.alcoholtracker.presantation.addmydrink

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.domain.entity.Icon
import com.utmaximur.alcoholtracker.domain.interactor.AddNewDrinkInteractor
import com.utmaximur.alcoholtracker.util.extension.empty
import com.utmaximur.alcoholtracker.util.formatDegree1f
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AddNewDrinkViewModel @Inject constructor(
    private var addNewDrinkInteractor: AddNewDrinkInteractor
) : ViewModel() {

    private var id: String = String.empty()
    private var photo: String = String.empty()
    private var name: String = String.empty()
    private var icon: String = String.empty()
    private var degreeList: ArrayList<String?> = ArrayList()
    private var volumeList: ArrayList<String?> = ArrayList()

    val drink: LiveData<Drink> by lazy {
        MutableLiveData()
    }

    fun onSaveButtonClick() {
        viewModelScope.launch {
            val drink = Drink(
                id,
                name,
                degreeList,
                volumeList,
                icon,
                photo
            )
            if (id == String.empty()) {
                val newDrink = drink.copy(id = getDrinkId())
                addNewDrinkInteractor.insertDrink(newDrink)
            } else {
                addNewDrinkInteractor.updateDrink(drink)
            }
        }
    }

    fun getIcons(): List<Icon> {
        return addNewDrinkInteractor.getIcons()
    }

    fun getVolumes(context: Context): List<String?> {
        val volume = context.resources.getStringArray(R.array.volume_array)
        return volume.toList()
    }

    private fun getDrinkId(): String = UUID.randomUUID().toString()

    fun getDoubleDegree(degree: Double, size: Int): List<String?> {
        degreeList.clear()
        var double = degree
        for (i in 0 until size * 2) {
            degreeList.add(double.formatDegree1f())
            double += 0.5
        }
        if (size == 0) {
            degreeList.add(double.formatDegree1f())
        }
        return degreeList
    }

    fun checkEmptyField(context: Context): String {
        when {
            photo.isEmpty() -> {
                return context.getString(R.string.empty_field_photo)
            }
            name.isEmpty() -> {
                return context.getString(R.string.empty_field_name)
            }
            icon.isEmpty() -> {
                return context.getString(R.string.empty_field_icon)
            }
            degreeList.isEmpty() -> {
                return context.getString(R.string.empty_field_degree)
            }
            volumeList.isEmpty() -> {
                return context.getString(R.string.empty_field_volume)
            }
        }
        return ""
    }

    fun setDrink(drink: Drink?) {

        val dataDrinks = drink
        (this.drink as MutableLiveData).value = dataDrinks

        id = drink?.id.toString()
        photo = drink?.photo.toString()
        name = drink?.drink.toString()
        degreeList = drink?.degree as ArrayList<String?>
        volumeList = drink.volume as ArrayList<String?>
        icon = drink.icon
    }

    fun setPhoto(photo: String) {
        this.photo = photo
    }

    fun setNameDrink(name: String) {
        this.name = name
    }

    fun setIcon(icon: String) {
        this.icon = icon
    }

    fun getVolumeList() = volumeList

    fun removeVolumeList(volume: String?) {
        volumeList.remove(volume)
    }

    fun addVolumeList(volume: String?) {
        volumeList.add(volume)
    }
}