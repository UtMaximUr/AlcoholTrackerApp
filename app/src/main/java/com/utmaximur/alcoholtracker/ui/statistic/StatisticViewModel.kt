package com.utmaximur.alcoholtracker.ui.statistic

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.repository.TrackRepository
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class StatisticViewModel(
    private var drinkRepository: DrinkRepository,
    private var trackRepository: TrackRepository
): ViewModel() {

    private var weekPeriod = "week"
    private var monthPeriod = "month"
    private var yearPeriod = "year"

    private var allAlcoholTrackList: ArrayList<AlcoholTrack>? = ArrayList()

    fun getAllDrink(): LiveData<MutableList<Drink>> {
        return  drinkRepository.getDrinks()
    }

    fun getAllTrack(): LiveData<MutableList<AlcoholTrack>> {
        return  trackRepository.getTracks()
    }

    fun loadTrackList(list: List<AlcoholTrack>?){
        allAlcoholTrackList?.addAll(list!!)
    }

    private fun getTrackList(): ArrayList<AlcoholTrack>?{
       return allAlcoholTrackList
    }

    fun getPriceListByPeriod(): List<String> {
        val priceList: ArrayList<String> = ArrayList()

        priceList.add(getSumPriceByPeriod(weekPeriod, allAlcoholTrackList!!))
        priceList.add(getSumPriceByPeriod(monthPeriod, allAlcoholTrackList!!))
        priceList.add(getSumPriceByPeriod(yearPeriod, allAlcoholTrackList!!))

        return priceList
    }

    private fun getSumPriceByPeriod(period: String, alcoholTrackList: List<AlcoholTrack>): String {
        val timezone = TimeZone.getDefault()
        val cal = Calendar.getInstance(timezone)
        var sumPrice = 0f
        when (period) {
            weekPeriod -> {
                cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                alcoholTrackList.forEach {
                    if (it.date > cal.timeInMillis) {
                        sumPrice += it.quantity * it.price
                    }
                }
            }
            monthPeriod -> {
                cal.set(Calendar.DAY_OF_MONTH, 1)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                alcoholTrackList.forEach {
                    if (it.date > cal.timeInMillis) {
                        sumPrice += it.quantity * it.price
                    }
                }
            }
            yearPeriod -> {
                cal.set(Calendar.DAY_OF_YEAR, 1)
                alcoholTrackList.forEach {
                    if (it.date > cal.timeInMillis) {
                        sumPrice += it.quantity * it.price
                    }
                }
            }
        }
        return sumPrice.toString()
    }

    fun getDrinksDrunkByMe(): Map<String, Int> {
        val drinksDrunkByMe: HashMap<String, Int> = HashMap()
        var count: Int
        getTrackList()?.forEach {
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

    fun getStatisticCountDays(context: Context): List<String> {
        val statisticCountDays: ArrayList<String> = ArrayList()
        statisticCountDays.add(getNumberOfDaysSinceTheLastDrink(context))
        statisticCountDays.add(getCountDayOffYear(context))

        return statisticCountDays
    }

    private fun getCountDayOffYear(context: Context): String {
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

    private fun getNumberOfDaysSinceTheLastDrink(context: Context): String {
        val countDays: HashSet<Int> = HashSet()
        val cal = Calendar.getInstance()
        val currentDay = cal.time.time
        var lastDrinkDay = 0L
        allAlcoholTrackList?.forEach {
            if (it.date > lastDrinkDay) {
                lastDrinkDay = it.date
            }
        }

        val count = TimeUnit.DAYS.convert(currentDay - lastDrinkDay, TimeUnit.MILLISECONDS)
        countDays.add(count.toInt())

        TimeUnit.DAYS.convert(currentDay - lastDrinkDay, TimeUnit.MILLISECONDS)

        return String.format(
            context.resources.getString(R.string.statistic_count_days_no_drink),
            context.resources.getQuantityString(
                R.plurals.plurals_day,
                countDays.first(),
                countDays.first()
            )
        )
    }
}