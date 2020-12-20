package com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter.impl

import android.content.Context
import com.applandeo.materialcalendarview.EventDay
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.ui.base.BasePresenter
import com.utmaximur.alcoholtracker.ui.calendar.interactor.CalendarInteractor
import com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter.CalendarPresenter
import com.utmaximur.alcoholtracker.ui.calendar.presentation.view.CalendarView
import java.util.*
import kotlin.collections.ArrayList

class CalendarPresenterImpl(private val interactor: CalendarInteractor) :
    BasePresenter<CalendarView>(),
    CalendarPresenter {

    override fun initRealmWithData(context: Context) {
        val drinks: ArrayList<Drink> = ArrayList()

        drinks.add(
            Drink(
                context.getString(R.string.enum_beer),
                getDoubleDegree(3.5, 50),
                R.array.volume_beer_array,
                R.raw.ic_beer,
                R.raw.beer
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_absent),
                getIntDegree(69, 15),
                R.array.volume_absent_array,
                R.raw.ic_absent,
                R.raw.absent
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_brandy),
                getIntDegree(39, 5),
                R.array.volume_brandy_array,
                R.raw.ic_brandy,
                R.raw.brandy
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_champagne),
                getIntDegree(8, 18),
                R.array.volume_champagne_array,
                R.raw.ic_champagne,
                R.raw.champagne
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_cocktail),
                getDoubleDegree(5.0, 20),
                R.array.volume_cocktail_array,
                R.raw.ic_cocktail,
                R.raw.coctail
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_cognac),
                getIntDegree(39, 5),
                R.array.volume_cognac_array,
                R.raw.ic_cognac,
                R.raw.cognac
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_liqueur),
                getIntDegree(14, 30),
                R.array.volume_liquor_array,
                R.raw.ic_liqueur,
                R.raw.liquor
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_tequila),
                getIntDegree(34, 20),
                R.array.volume_tequila_array,
                R.raw.ic_tequila,
                R.raw.tequila
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_vodka),
                getIntDegree(37, 18),
                R.array.volume_vodka_array,
                R.raw.ic_vodka,
                R.raw.vodka
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_whiskey),
                getIntDegree(39, 10),
                R.array.volume_whiskey_array,
                R.raw.ic_whiskey,
                R.raw.whiskey
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_gin),
                getIntDegree(37, 10),
                R.array.volume_gin_array,
                R.raw.ic_gin,
                R.raw.gin
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_wine),
                getIntDegree(9, 14),
                R.array.volume_wine_array,
                R.raw.ic_wine,
                R.raw.wine
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_cider),
                getDoubleDegree(6.0, 20),
                R.array.volume_cider_array,
                R.raw.ic_cider,
                R.raw.cider
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_shot),
                getIntDegree(35, 40),
                R.array.volume_shots_array,
                R.raw.ic_shot,
                R.raw.shots
            )
        )
        drinks.add(
            Drink(
                context.getString(R.string.enum_rum),
                getIntDegree(37, 12),
                R.array.volume_rum_array,
                R.raw.ic_rum,
                R.raw.rum
            )
        )

//        val jsonHelper = JSONHelper()
//        jsonHelper.exportToJSON(context, drinks)
        interactor.initRealmWithData(drinks)
    }

    override fun getDrink(date: Long): AlcoholTrack? {
        return interactor.getDrink(date)
    }

    override fun getDrinkByMonth(month: Long): MutableList<AlcoholTrack> {
        val list: MutableList<AlcoholTrack> = interactor.getDrinkByMonth(month)
        list.sortBy { it.date }
        return list
    }

    override fun deleteDrink(id: String) {
        interactor.deleteDrink(id)
    }

    override fun viewIsReady() {

    }

    private fun getDoubleDegree(degree: Double, size: Int): List<String?> {
        val nums: Array<String?> = arrayOfNulls(size)
        var double = degree
        for (i in 0 until size) {
            double += 0.5
            val format: String = String.format("%.1f", double)
            nums[i] = format
        }
        return nums.toList()
    }

    private fun getIntDegree(degree: Int, size: Int): List<String?> {
        val nums: Array<String?> = arrayOfNulls(size)
        var n = degree
        for (i in 0 until size) {
            n += 1
            nums[i] = n.toString()
        }
        return nums.toList()
    }

    override fun getAlcoholTrackByDay(eventDay: Long): MutableList<AlcoholTrack> {
        val alcoholTrack: ArrayList<AlcoholTrack> = ArrayList()
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = eventDay
        val startTimeDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val endTimeDay: Int = startTimeDay + 1
        val month: Int = calendar.get(Calendar.MONTH)
        interactor.getDrinkByMonth(eventDay).forEach {

            calendar.timeInMillis = it.date
            if (calendar.get(Calendar.DAY_OF_MONTH) in startTimeDay until endTimeDay && calendar.get(
                    Calendar.MONTH
                ) == month
            ) {
                alcoholTrack.add(it)
            }
        }
        return alcoholTrack
    }

    override fun setIconOnDate() {
        val events: MutableList<EventDay> = java.util.ArrayList()
        getDrinkByMonth(Date().time).forEach {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it.date
            events.add(EventDay(calendar, it.icon))
        }
        view?.setIconOnDate(events)
    }
}