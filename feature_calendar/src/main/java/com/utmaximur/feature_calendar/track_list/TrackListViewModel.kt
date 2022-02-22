package com.utmaximur.feature_calendar.track_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.utils.setPostValue
import com.utmaximur.domain.entity.Track
import com.utmaximur.domain.interactor.TrackListInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackListViewModel @Inject constructor(private val trackListInteractor: TrackListInteractor) :
    ViewModel() {

    val tracksByDay: LiveData<List<Track>> by lazy {
        MutableLiveData()
    }

    fun trackByDayChange(date: Long) = viewModelScope.launch {
        val dataTracks = dataAlcoholTrackByDay(date)
        tracksByDay.setPostValue(dataTracks)
    }

    fun onDeleteDrink(trackCalendar: Track) {
        viewModelScope.launch {
            trackListInteractor.deleteTrack(trackCalendar)
            updateTracksByDay(trackCalendar.date)
        }
    }

    fun clearTrackByDay() {
        tracksByDay.setPostValue(listOf())
    }

    private suspend fun dataAlcoholTrackByDay(eventDay: Long): List<Track> {
        return trackListInteractor.getAlcoholTrackByDay(eventDay)
    }

    private fun updateTracksByDay(eventDay: Long) = viewModelScope.launch {
        val dataTracks = dataAlcoholTrackByDay(eventDay)
        tracksByDay.setPostValue(dataTracks)
    }
}