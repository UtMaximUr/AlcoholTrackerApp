package com.utmaximur.alcoholtracker.ui.addmydrink

import android.content.Context
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.model.Icon
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.repository.AssetsRepository
import com.utmaximur.alcoholtracker.util.formatDegree1f
import java.util.*

class AddNewDrinkViewModel(
    private var drinkRepository: DrinkRepository,
    private var assetsRepository: AssetsRepository
) : ViewModel() {

    var id: String = ""
    var photo: String = ""
    var nameDrink: String = ""
    var icon: String = ""
    var degreeList: ArrayList<String?> = ArrayList()
    var volumeList: ArrayList<String?> = ArrayList()

    fun onSaveButtonClick(drink: Drink) {
        if (id == "") {
            drink.id = getDrinkId()
            drinkRepository.insertDrink(drink)
        } else {
            drinkRepository.updateDrink(drink)
        }
    }

    fun getIcons(): List<Icon> {
        return assetsRepository.getIcons()
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
            nameDrink.isEmpty() -> {
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
}