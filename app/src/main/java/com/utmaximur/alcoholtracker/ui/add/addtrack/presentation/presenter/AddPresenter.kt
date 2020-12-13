package com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.presenter

import android.content.Context
import androidx.lifecycle.LiveData
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.view.AddView
import com.utmaximur.alcoholtracker.ui.base.MvpPresenter
import java.util.*
import kotlin.collections.HashMap

interface AddPresenter : MvpPresenter<AddView> {

    fun getLiveData(): LiveData<HashMap<String, Long>>

    fun onSaveButtonClick()

    fun getAllDrink(): MutableList<Drink>

    fun getFloatDegree(): Array<String?>

    fun checkIsEmptyField():Boolean

    fun getTotalLitres(pos: Int): CharSequence?

    fun getTotalMoney(): CharSequence?

    fun setDateOnButton(context: Context, date: Date): String

}