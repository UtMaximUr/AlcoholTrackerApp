package com.utmaximur.alcoholtracker.dagger.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.repository.TrackRepository
import com.utmaximur.alcoholtracker.ui.calculator.view.CalculatorViewModel
import com.utmaximur.alcoholtracker.ui.calendar.presentation.view.CalendarViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalculatorViewModelFactory  @Inject constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalculatorViewModel()  as T
    }
}