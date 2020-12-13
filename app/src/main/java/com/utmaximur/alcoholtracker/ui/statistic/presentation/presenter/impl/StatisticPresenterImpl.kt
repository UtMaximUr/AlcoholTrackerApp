package com.utmaximur.alcoholtracker.ui.statistic.presentation.presenter.impl

import android.content.Context
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.ui.base.BasePresenter
import com.utmaximur.alcoholtracker.ui.statistic.interactor.StatisticInteractor
import com.utmaximur.alcoholtracker.ui.statistic.presentation.presenter.StatisticPresenter
import com.utmaximur.alcoholtracker.ui.statistic.presentation.view.StatisticView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class StatisticPresenterImpl(private val interactor: StatisticInteractor) :
    BasePresenter<StatisticView>(),
    StatisticPresenter {

    private var weekPeriod = "week"
    private var monthPeriod = "month"
    private var yearPeriod = "year"

    private var allAlcoholTrackList: List<AlcoholTrack>? = null

    override fun getAllDrink(): MutableList<Drink> {
        return interactor.getAllDrink()
    }

    override fun getAllAlcoholTrack(): MutableList<AlcoholTrack> {
        return interactor.getAllAlcoholTrack()
    }

    override fun getPriceListByPeriod(): List<String> {
        val priceList: ArrayList<String> = ArrayList()

        priceList.add(getSumPriceByPeriod(weekPeriod, allAlcoholTrackList!!))
        priceList.add(getSumPriceByPeriod(monthPeriod, allAlcoholTrackList!!))
        priceList.add(getSumPriceByPeriod(yearPeriod, allAlcoholTrackList!!))

        return priceList
    }


    override fun viewIsReady() {
        allAlcoholTrackList = getAllAlcoholTrack()
    }

    private fun getSumPriceByPeriod(period: String, alcoholTrackList: List<AlcoholTrack>): String {
        val timezone = TimeZone.getDefault()
        val cal = Calendar.getInstance(timezone)
        var sumPrice = 0f
        when (period) {
            weekPeriod -> {
                cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
                alcoholTrackList.forEach {
                    if (it.date > cal.timeInMillis) {
                        sumPrice += it.quantity * it.price
                    }
                }
            }
            monthPeriod -> {
                cal.set(Calendar.DAY_OF_MONTH, 1)
                alcoholTrackList.forEach {
                    if (it.date > cal.timeInMillis) {
                        sumPrice += it.quantity * it.price
                    }
                }
            }
            yearPeriod -> {
                alcoholTrackList.forEach {
                    sumPrice += it.quantity * it.price
                }
            }
        }
        return sumPrice.toString()
    }

    override fun getCountDayOffYear(context: Context): String {
        val countDays: HashSet<Int> = HashSet()
        val cal = Calendar.getInstance()
        allAlcoholTrackList?.forEach {
            cal.timeInMillis = it.date
            countDays.add(cal.get(Calendar.DAY_OF_MONTH))
        }
        return String.format(
            context.resources.getString(R.string.statistic_count_days),
            context.resources.getQuantityString(
                R.plurals.plurals_day,
                countDays.size,
                countDays.size
            ),
            Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR)
        )
    }

    override fun getDrinksDrunkByMe(): Map<String, Int> {
        val drinksDrunkByMe: HashMap<String, Int> = HashMap()
        var count: Int
        allAlcoholTrackList?.forEach {
            if (drinksDrunkByMe.containsKey(it.drink)) {
                count = drinksDrunkByMe[it.drink]!!.toInt()
                count += it.quantity
                drinksDrunkByMe[it.drink] = count
            } else {
                drinksDrunkByMe[it.drink] = it.quantity
            }
        }
        return drinksDrunkByMe
    }
}