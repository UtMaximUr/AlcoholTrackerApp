package com.utmaximur.alcoholtracker.ui.addmydrink.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.model.Icon
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.repository.IconRepository
import java.util.*
import kotlin.collections.ArrayList

class AddNewDrinkViewModel(private var drinkRepository: DrinkRepository, private var iconRepository: IconRepository): ViewModel() {

    var id: String = ""
    var volumeList: List<String?> = ArrayList()
    var icon: String = ""
    var photo: String = ""

    fun onSaveButtonClick(drink: Drink){
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

    private fun getDrinkId(): String = UUID.randomUUID().toString()

    fun getDoubleDegree(degree: Double, size: Int): List<String?> {
        val nums: Array<String?> = arrayOfNulls(size * 2)
        var double = degree - 0.5
        for (i in 0 until size * 2) {
            double += 0.5
            val format: String = String.format("%.1f", double)
            nums[i] = format.replace(",",".")
        }
        return nums.toList()
    }
}