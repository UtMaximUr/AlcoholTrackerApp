package com.utmaximur.alcoholtracker.ui.statistic.presentation.presenter

import android.content.Context
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.ui.base.MvpPresenter
import com.utmaximur.alcoholtracker.ui.statistic.presentation.view.StatisticView

interface StatisticPresenter : MvpPresenter<StatisticView> {

    fun getAllDrink(): MutableList<Drink>

    fun getAllAlcoholTrack(): MutableList<AlcoholTrack>

    fun getPriceListByPeriod(): List<String>

    fun getCountDayOffYear(context: Context): String

    fun getDrinksDrunkByMe(): Map<String, Int>
}