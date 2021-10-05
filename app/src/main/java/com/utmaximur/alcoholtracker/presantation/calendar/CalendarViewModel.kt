package com.utmaximur.alcoholtracker.presantation.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.domain.interactor.CalendarInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject


open class CalendarViewModel @Inject constructor( private var calendarInteractor: CalendarInteractor) : ViewModel() {

    private var selectDate = 0L

    fun setSelectDate(selectDate: Long) {
        this.selectDate = selectDate
    }

    fun getSelectDate(): Long {
        return selectDate
    }

    val tracks: LiveData<List<Track>> by lazy {
        MutableLiveData()
    }

    val tracksByDay: LiveData<List<Track>> by lazy {
        MutableLiveData()
    }

    fun initTracks() = viewModelScope.launch {
        val dataTracks = getTracks()
        (tracks as MutableLiveData).value = dataTracks
    }

    fun initTracksByDay(date: Long) = viewModelScope.launch {
        val dataTracks = getAlcoholTrackByDay(date)
        (tracksByDay as MutableLiveData).value = dataTracks
    }

    private fun updateTracks() = viewModelScope.launch {
        val dataTracks = getTracks()
        (tracks as MutableLiveData).value = dataTracks
    }

    private fun updateTracksByDay(eventDay: Long) = viewModelScope.launch {
        val dataTracks = getAlcoholTrackByDay(eventDay)
        (tracksByDay as MutableLiveData).value = dataTracks
    }

    suspend fun getTrack(date: Long): Track {
        return calendarInteractor.getTrack(date)
    }

    private suspend fun getTracks(): List<Track> {
        return calendarInteractor.getTracks()
    }

    suspend fun deleteDrink(trackCalendar: Track) {
        calendarInteractor.deleteTrack(trackCalendar)
        updateTracks()
        updateTracksByDay(trackCalendar.date)
    }

    private suspend fun getAlcoholTrackByDay(
        eventDay: Long
    ): List<Track> {
        return calendarInteractor.getAlcoholTrackByDay(eventDay)
    }
}