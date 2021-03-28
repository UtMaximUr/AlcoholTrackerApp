package com.utmaximur.alcoholtracker.ui.addmydrink

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.model.Icon
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.repository.IconRepository
import com.utmaximur.alcoholtracker.util.formatDegree1f
import java.util.*

class AddNewDrinkViewModel(
    private var drinkRepository: DrinkRepository,
    private var iconRepository: IconRepository
) : ViewModel() {

    var id: String = ""
    var volumeList: ArrayList<String?> = ArrayList()
    var icon: String = ""
    var photo: String = ""

    fun onSaveButtonClick(drink: Drink) {
        if (id == "") {
            drink.id = getDrinkId()
            drinkRepository.insertDrink(drink)
        } else {
            drinkRepository.updateDrink(drink)
        }
    }

    fun getIcons(): LiveData<List<Icon>> {
        return iconRepository.getIcons()
    }

    fun getVolumes(context: Context): List<String?> {
        val volume = context.resources.getStringArray(R.array.volume_array)
        return volume.toList()
    }

    private fun getDrinkId(): String = UUID.randomUUID().toString()

    fun getDoubleDegree(degree: Double, size: Int): List<String?> {
        val degrees: Array<String?> = arrayOfNulls(size * 2)
        var double = degree - 0.5
        for (i in 0 until size * 2) {
            double += 0.5
            degrees[i] = double.formatDegree1f()
        }
        return degrees.toList()
    }
}