package com.utmaximur.alcoholtracker.presentation.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.domain.interactor.CalendarInteractor
import com.utmaximur.alcoholtracker.presentation.calendar.state.TextState
import com.utmaximur.alcoholtracker.util.setPostValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
open class CalendarViewModel @Inject constructor(private var calendarInteractor: CalendarInteractor) :
    ViewModel() {

    val tracks: LiveData<List<Track>> by lazy {
        MutableLiveData()
    }

    val textState: LiveData<TextState> by lazy {
        MutableLiveData()
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

    private suspend fun dataAlcoholTrackByDay(eventDay: Long): List<Track> {
        return calendarInteractor.getAlcoholTrackByDay(eventDay)
    }

    fun initTextState(date: Long) = viewModelScope.launch {
        val dataTracks = dataTracks()
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