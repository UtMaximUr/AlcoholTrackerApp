package com.utmaximur.alcoholtracker.ui.calendar.presentation.view

import com.applandeo.materialcalendarview.EventDay
import com.utmaximur.alcoholtracker.ui.base.MvpView

interface CalendarView : MvpView {

    fun setIconOnDate(events: MutableList<EventDay>)

}