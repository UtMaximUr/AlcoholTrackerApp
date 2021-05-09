package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.repository.TrackRepository
import com.utmaximur.alcoholtracker.ui.calendar.CalendarViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarViewModelFactory  @Inject constructor(private var trackRepository: TrackRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalendarViewModel(trackRepository)  as T
    }
}