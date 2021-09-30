package com.utmaximur.alcoholtracker.domain.mapper


import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.domain.entity.TrackCalendar
import java.util.*
import javax.inject.Inject

class CalendarMapper @Inject constructor() {

    fun getAlcoholTrackByDay(
        tracksList: List<Track>,
        eventDay: Long
    ): List<TrackCalendar> {

        val tracks: ArrayList<TrackCalendar> = ArrayList()

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = eventDay
        val startTimeDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val endTimeDay: Int = startTimeDay + 1
        val month: Int = calendar.get(Calendar.MONTH)

        tracks.clear()
        tracksList.forEach {
            calendar.timeInMillis = it.date
            if (calendar.get(Calendar.DAY_OF_MONTH) in startTimeDay until endTimeDay && calendar.get(
                    Calendar.MONTH
                ) == month
            ) {
                tracks.add(map(it))
            }
        }

        return tracks
    }

    fun map(domain: TrackCalendar): Track {
        return Track(
            id = domain.id,
            drink = domain.drink,
            volume = domain.volume,
            quantity = domain.quantity,
            degree = domain.degree,
            event = domain.event,
            price = domain.price,
            date = domain.date,
            icon = domain.icon
        )
    }

    fun map(domain: Track): TrackCalendar {
        return TrackCalendar(
            id = domain.id,
            drink = domain.drink,
            volume = domain.volume,
            quantity = domain.quantity,
            degree = domain.degree,
            event = domain.event,
            price = domain.price,
            date = domain.date,
            icon = domain.icon
        )
    }

    fun mapList(tracksList: List<Track>): List<TrackCalendar> {
        return tracksList.map { map(it) }
    }
}