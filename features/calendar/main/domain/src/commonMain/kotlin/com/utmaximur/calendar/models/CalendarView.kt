package com.utmaximur.calendar.models

import calendar.domain.resources.Res
import calendar.domain.resources.ic_calendar_day_view
import calendar.domain.resources.ic_calendar_month_view
import calendar.domain.resources.schedule_day_view
import calendar.domain.resources.schedule_month_view
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class CalendarView(
    val title: StringResource,
    val icon: DrawableResource
) {
    DAY_VIEW(
        Res.string.schedule_day_view,
        Res.drawable.ic_calendar_day_view
    ),
    MONTH_VIEW(
        Res.string.schedule_month_view,
        Res.drawable.ic_calendar_month_view
    )
}