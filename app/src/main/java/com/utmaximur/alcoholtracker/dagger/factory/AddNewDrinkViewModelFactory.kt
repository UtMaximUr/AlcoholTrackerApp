package com.utmaximur.alcoholtracker.dagger.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.ui.addmydrink.view.AddNewDrinkViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddNewDrinkViewModelFactory @Inject constructor(private var drinkRepository: DrinkRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddNewDrinkViewModel(drinkRepository)  as T
    }
}