package com.utmaximur.alcoholtracker.ui.add.presentation.view

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.repository.TrackRepository
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddViewModel(private var drinkRepository: DrinkRepository,
                   private var trackRepository: TrackRepository) : ViewModel() {

    var id: String = ""
    var drink: String = ""
    var volume: String = ""
    val quantity: Int = 0
    var degree: String = ""
    var price: Float = 0.0f
    var date: Long = 0L
    var icon: Int = 0
    var drinks: List<Drink> = ArrayList()
    var volums: List<String> = ArrayList()
    var degrees: List<String?> = ArrayList()


    fun onSaveButtonClick(alcoholTrack: AlcoholTrack){
        if(alcoholTrack.id == ""){
            alcoholTrack.id = getTrackId()
            trackRepository.insertTrack(alcoholTrack)
        }else {
            trackRepository.updateTrack(alcoholTrack)
        }
    }

    fun getAllDrink(): LiveData<MutableList<Drink>> {
        return  drinkRepository.getDrinks()
    }

    private fun getTrackId(): String = UUID.randomUUID().toString()

     fun checkIsEmptyField(price: Float, date: Long): Boolean {
        return !(price == 0.0f || date == 0L)
    }

     fun checkIsEmptyFieldPrice(price: Float): Boolean {
        return price != 0.0f
    }

     fun getTotalMoney(quantity: Int, price: Float): String? {
        return (quantity * price.toString().toDouble()).toString()
    }

     fun setDateOnButton(context: Context, date: Date): String {
        val sdf = SimpleDateFormat(
            context.resources.getString(R.string.date_format_pattern),
            Locale.getDefault()
        )
        return String.format("%s", sdf.format(date))
    }

     fun getFloatDegree(): Array<String?> {
        val nums: Array<String?> = arrayOfNulls(11)
        var double = 3.5
        for (i in 0..10) {
            double += 0.5
            val format: String = String.format("%.1f", double)
            nums[i] = format
        }
        return nums
    }

    fun getFormatString(context: Context, date: Long): String{
        val sdf = SimpleDateFormat(context.resources.getString(R.string.date_format_pattern), Locale.getDefault())
        return String.format("%s", sdf.format(Date(date)))
    }
}