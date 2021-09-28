package com.utmaximur.alcoholtracker.domain.mapper


import com.utmaximur.alcoholtracker.data.dbo.DrinkDBO
import com.utmaximur.alcoholtracker.data.dbo.TrackDBO
import com.utmaximur.alcoholtracker.domain.entity.DrinkStatistic
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class StatisticsMapper @Inject constructor() {

    private var weekPeriod = "week"
    private var monthPeriod = "month"
    private var yearPeriod = "year"

    fun mapDrinks(trackDBOList: List<TrackDBO>, drinks: List<DrinkDBO>): List<DrinkStatistic> {
        return drinks.map {
            DrinkStatistic(it.drink, it.icon, mapDrinksDrunkByMe(trackDBOList, it))
        }
    }

    fun mapPriceListByPeriod(trackDBOList: List<TrackDBO>): List<String> {
        val priceList: ArrayList<String> = ArrayList()

        priceList.add(mapSumPriceByPeriod(weekPeriod, trackDBOList))
        priceList.add(mapSumPriceByPeriod(monthPeriod, trackDBOList))
        priceList.add(mapSumPriceByPeriod(yearPeriod, trackDBOList))

        return priceList
    }

    private fun mapSumPriceByPeriod(period: String, trackDBOList: List<TrackDBO>): String {
        val timezone = TimeZone.getDefault()
        val cal = Calendar.getInstance(timezone)
        var sumPrice = 0f
        when (period) {
            weekPeriod -> {
                cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                trackDBOList.forEach {
                    if (it.date > cal.timeInMillis) {
                        sumPrice += it.quantity * it.price
                    }
                }
            }
            monthPeriod -> {
                cal.set(Calendar.DAY_OF_MONTH, 1)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                trackDBOList.forEach {
                    if (it.date > cal.timeInMillis) {
                        sumPrice += it.quantity * it.price
                    }
                }
            }
            yearPeriod -> {
                cal.set(Calendar.DAY_OF_YEAR, 1)
                trackDBOList.forEach {
                    if (it.date > cal.timeInMillis) {
                        sumPrice += it.quantity * it.price
                    }
                }
            }
        }
        return sumPrice.toString()
    }

    private fun mapDrinksDrunkByMe(trackDBOList: List<TrackDBO>, drink: DrinkDBO): Int {
        val drinksDrunkByMe: HashMap<String, Int> = HashMap()
        var count: Int
        trackDBOList.forEach {
            if (drinksDrunkByMe.containsKey(it.drink)) {
                count = drinksDrunkByMe[it.drink]!!.toInt()
                count += it.quantity
                drinksDrunkByMe[it.drink] = count
            } else {
                drinksDrunkByMe[it.drink] = it.quantity
            }
        }
        return if (drinksDrunkByMe.containsKey(drink.drink)) {
            drinksDrunkByMe[drink.drink]!!
        } else {
            0
        }
    }

    fun mapStatisticCountDays(trackDBOList: List<TrackDBO>): List<Int> {
        val statisticCountDays: ArrayList<Int> = ArrayList()
        if (mapNumberOfDaysSinceTheLastDrink(trackDBOList) != 0) { //null) {
            statisticCountDays.add(mapNumberOfDaysSinceTheLastDrink(trackDBOList))
        }
        statisticCountDays.add(mapCountDayOffYear(trackDBOList))

        return statisticCountDays
    }

    private fun mapCountDayOffYear(trackDBOList: List<TrackDBO>): Int {
        val countDays: HashSet<Int> = HashSet()
        val cal = Calendar.getInstance()
        trackDBOList.forEach {
            cal.timeInMillis = it.date
            countDays.add(cal.get(Calendar.DAY_OF_MONTH))
        }
//        return String.format(
//            context.resources.getString(R.string.statistic_count_days),
//            context.resources.getQuantityString(
//                R.plurals.plurals_day,
//                countDays.size,
//                countDays.size
//            ),
//            Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR)
//        )
        return countDays.size
    }

    private fun mapNumberOfDaysSinceTheLastDrink(trackDBOList: List<TrackDBO>): Int {
        val countDays: HashSet<Int> = HashSet()
        val cal = Calendar.getInstance()
        val currentDay = cal.time.time
        var lastDrinkDay = 0L
        if (trackDBOList.isEmpty()) {
            return 0//null
        } else {
            trackDBOList.forEach {
                if (it.date > lastDrinkDay) {
                    lastDrinkDay = it.date
                }
            }

            val count = TimeUnit.DAYS.convert(currentDay - lastDrinkDay, TimeUnit.MILLISECONDS)
            countDays.add(count.toInt())

            TimeUnit.DAYS.convert(currentDay - lastDrinkDay, TimeUnit.MILLISECONDS)

//            return String.format(
//                context.resources.getString(R.string.statistic_count_days_no_drink),
//                context.resources.getQuantityString(
//                    R.plurals.plurals_day,
//                    countDays.first(),
//                    countDays.first()
//                )
//            )
            return countDays.first()
        }
    }
}