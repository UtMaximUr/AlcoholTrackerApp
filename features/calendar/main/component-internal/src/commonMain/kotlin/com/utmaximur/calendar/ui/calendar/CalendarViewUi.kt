package com.utmaximur.calendar.ui.calendar

import com.utmaximur.calendar.models.CalendarView
import features.calendar.main.Res
import features.calendar.main.ic_calendar_day_view
import features.calendar.main.ic_calendar_month_view
import features.calendar.main.schedule_day_view
import features.calendar.main.schedule_month_view

val CalendarView.title get() = when(this) {
    CalendarView.DAY_VIEW ->  Res.string.schedule_day_view
    CalendarView.MONTH_VIEW -> Res.string.schedule_month_view
}

val CalendarView.icon get() = when(this) {
    CalendarView.DAY_VIEW -> Res.drawable.ic_calendar_day_view
    CalendarView.MONTH_VIEW -> Res.drawable.ic_calendar_month_view
}