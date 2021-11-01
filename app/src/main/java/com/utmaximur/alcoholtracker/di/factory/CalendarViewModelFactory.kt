package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.domain.interactor.CalendarInteractor
import com.utmaximur.alcoholtracker.presentation.calendar.CalendarViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarViewModelFactory  @Inject constructor(private var calendarInteractor: CalendarInteractor) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalendarViewModel(calendarInteractor)  as T
    }
}