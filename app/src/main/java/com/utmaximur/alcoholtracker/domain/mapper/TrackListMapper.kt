package com.utmaximur.alcoholtracker.domain.mapper


import com.utmaximur.alcoholtracker.domain.entity.EventDay
import com.utmaximur.alcoholtracker.domain.entity.Track
import java.util.*
import javax.inject.Inject

class TrackListMapper @Inject constructor() {

    fun getEventsDayList(tracksList: List<Track>): List<EventDay> {
        return tracksList.map { track -> EventDay(track.date, track.icon) }
    }

    fun getAlcoholTrackByDay(
        tracksList: List<Track>,
        eventDay: Long
    ): List<Track> {

        val tracks: ArrayList<Track> = ArrayList()

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
                tracks.add(it)
            }
        }

        return tracks
    }
}