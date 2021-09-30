package com.utmaximur.alcoholtracker.presantation.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.alcoholtracker.domain.entity.TrackCalendar
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

    val tracks: LiveData<List<TrackCalendar>> by lazy {
        MutableLiveData()
    }

    fun initTracks() = viewModelScope.launch {

        val dataTracks = getTracks()
        (tracks as MutableLiveData).value = dataTracks

    }

    suspend fun getTrack(date: Long): TrackCalendar {
        return calendarInteractor.getTrack(date)
    }

    private suspend fun getTracks(): List<TrackCalendar> {
        return calendarInteractor.getTracks()
    }

    suspend fun deleteDrink(trackCalendar: TrackCalendar) {
        calendarInteractor.deleteTrack(trackCalendar)
    }

    suspend fun getAlcoholTrackByDay(
        eventDay: Long
    ): List<TrackCalendar> {
        return calendarInteractor.getAlcoholTrackByDay(eventDay)
    }
}