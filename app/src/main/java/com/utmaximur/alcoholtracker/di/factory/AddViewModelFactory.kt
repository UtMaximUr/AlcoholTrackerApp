package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.data.repository.DrinkRepository
import com.utmaximur.alcoholtracker.data.repository.TrackRepository
import com.utmaximur.alcoholtracker.presantation.add.AddViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddViewModelFactory  @Inject constructor(private var drinkRepository: DrinkRepository,
                                               private var trackRepository: TrackRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddViewModel(drinkRepository, trackRepository)  as T
    }
}