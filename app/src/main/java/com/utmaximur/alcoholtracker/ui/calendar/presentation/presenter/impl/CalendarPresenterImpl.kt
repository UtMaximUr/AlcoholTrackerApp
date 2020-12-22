package com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter.impl

import com.applandeo.materialcalendarview.EventDay
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.ui.base.BasePresenter
import com.utmaximur.alcoholtracker.ui.calendar.interactor.CalendarInteractor
import com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter.CalendarPresenter
import com.utmaximur.alcoholtracker.ui.calendar.presentation.view.CalendarView
import java.util.*
import kotlin.collections.ArrayList

class CalendarPresenterImpl(private val interactor: CalendarInteractor) :
    BasePresenter<CalendarView>(),
    CalendarPresenter {

    override fun getDrink(date: Long): AlcoholTrack? {
        return interactor.getDrink(date)
    }

    override fun getTracks(): MutableList<AlcoholTrack> {
        val list: MutableList<AlcoholTrack> = interactor.getTracks()
        list.sortBy {
            it.date }
        return list
    }

    override fun deleteDrink(alcoholTrack: AlcoholTrack) {
        interactor.deleteDrink(alcoholTrack)
    }

    override fun viewIsReady() {

    }

    override fun getAlcoholTrackByDay(eventDay: Long): MutableList<AlcoholTrack> {
        val alcoholTrack: ArrayList<AlcoholTrack> = ArrayList()
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = eventDay
        val startTimeDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val endTimeDay: Int = startTimeDay + 1
        val month: Int = calendar.get(Calendar.MONTH)
        interactor.getTracks().forEach {

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
        getTracks().forEach {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it.date
            events.add(EventDay(calendar, it.icon))
        }
        view?.setIconOnDate(events)
    }
}