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

    val tracks: LiveData<List<Track>> by lazy {
        MutableLiveData()
    }

    init {
        viewModelScope.launch {
            val dataTracks = dataTracks()
            (tracks as MutableLiveData).value = dataTracks
        }
    }

    private suspend fun dataTracks(): List<Track> {
        return calendarInteractor.getTracks()
    }

    fun updateTracks() {
        viewModelScope.launch {
            val dataTracks = dataTracks()
            (tracks as MutableLiveData).value = dataTracks
        }
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