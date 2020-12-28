package com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.view.impl

import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.data.model.Drink
import java.util.*
import kotlin.collections.ArrayList

class AddViewModel : ViewModel() {

    var id: String = ""
    var drink: String = ""
    var volume: String = ""
    val quantity: Int = 0
    var degree: String = ""
    val price: Float = 0.0f
    var date: Long = 0L
    var icon: Int = 0
    var drinks: List<Drink> = ArrayList()
    var volums: List<String> = ArrayList()
    var degrees: List<String?> = ArrayList()

}