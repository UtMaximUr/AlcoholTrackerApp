package com.utmaximur.calendar.models

import features.calendar.main.domain.Res
import features.calendar.main.domain.ic_calendar_day_view
import features.calendar.main.domain.ic_calendar_month_view
import features.calendar.main.domain.schedule_day_view
import features.calendar.main.domain.schedule_month_view
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
    );

    fun toggle() = when (this) {
        DAY_VIEW -> MONTH_VIEW
        MONTH_VIEW -> DAY_VIEW
    }
}