package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.repository.DrinkRepository
import com.utmaximur.alcoholtracker.repository.AssetsRepository
import com.utmaximur.alcoholtracker.ui.addmydrink.AddNewDrinkViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddNewDrinkViewModelFactory @Inject constructor(private var drinkRepository: DrinkRepository, private var assetsRepository: AssetsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddNewDrinkViewModel(drinkRepository, assetsRepository)  as T
    }
}