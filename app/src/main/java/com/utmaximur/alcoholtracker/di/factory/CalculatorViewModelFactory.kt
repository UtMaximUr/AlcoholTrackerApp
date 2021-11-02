package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.presentation.calculator.CalculatorViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalculatorViewModelFactory  @Inject constructor() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalculatorViewModel()  as T
    }
}