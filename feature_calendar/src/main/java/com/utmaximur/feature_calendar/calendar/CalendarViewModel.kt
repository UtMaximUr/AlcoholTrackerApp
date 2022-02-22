package com.utmaximur.feature_calendar.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.feature_calendar.calendar.state.TextState
import com.utmaximur.utils.setPostValue
import com.utmaximur.domain.entity.EventDay
import com.utmaximur.domain.entity.Track
import com.utmaximur.domain.interactor.CalendarInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
open class CalendarViewModel @Inject constructor(private var calendarInteractor: CalendarInteractor) :
    ViewModel() {

    val tracks: LiveData<List<EventDay>> by lazy {
        MutableLiveData()
    }

    val textState: LiveData<TextState> by lazy {
        MutableLiveData()
    }

    private suspend fun dataTracks(): List<EventDay> {
        return calendarInteractor.getTracks()
    }

    suspend fun fetchEvents(date: Long) {
        val dataTracks = dataTracks()
        (tracks as MutableLiveData).value = dataTracks
        if (dataTracks.isEmpty()) {
            textState.setPostValue(TextState.AddPressToStart())
        } else {
            val dataAlcoholTrackByDay = dataAlcoholTrackByDay(date)
            if (dataAlcoholTrackByDay.isEmpty()) {
                textState.setPostValue(TextState.TracksEmpty())
            } else {
                textState.setPostValue(TextState.SelectDay())
            }
        }
    }

    private suspend fun dataAlcoholTrackByDay(eventDay: Long): List<Track> {
        return calendarInteractor.getAlcoholTrackByDay(eventDay)
    }

    suspend fun isTrackByDayEmpty(date: Long): Boolean {
        val dataTracks = dataAlcoholTrackByDay(date)
        if (dataTracks.isEmpty()) {
            textState.setPostValue(TextState.TracksEmpty())
        } else {
            textState.setPostValue(TextState.SelectDay())
        }
        return dataTracks.isEmpty()
    }
}