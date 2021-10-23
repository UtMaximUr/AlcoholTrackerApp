package com.utmaximur.alcoholtracker.presentation.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.domain.interactor.CalendarInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject


open class CalendarViewModel @Inject constructor(private var calendarInteractor: CalendarInteractor) :
    ViewModel() {

    val tracksByDay: LiveData<List<Track>> by lazy {
        MutableLiveData()
    }

    fun dataTracks(): LiveData<List<Track>> {
        return calendarInteractor.getTracks()
    }

    suspend fun dataAlcoholTrackByDay(
        eventDay: Long
    ): List<Track> {
        return calendarInteractor.getAlcoholTrackByDay(eventDay)
    }

    fun initTracksByDay(date: Long) = viewModelScope.launch {
        val dataTracks = dataAlcoholTrackByDay(date)
        (tracksByDay as MutableLiveData).value = dataTracks
    }

    private fun updateTracksByDay(eventDay: Long) = viewModelScope.launch {
        val dataTracks = dataAlcoholTrackByDay(eventDay)
        (tracksByDay as MutableLiveData).value = dataTracks
    }

    suspend fun dataTrack(date: Long): Track {
        return calendarInteractor.getTrack(date)
    }

    suspend fun onDeleteDrink(trackCalendar: Track) {
        calendarInteractor.deleteTrack(trackCalendar)
        updateTracksByDay(trackCalendar.date)
    }
}