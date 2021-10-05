package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.data.repository.DrinkRepository
import com.utmaximur.alcoholtracker.data.repository.AssetsRepository
import com.utmaximur.alcoholtracker.domain.interactor.AddNewDrinkInteractor
import com.utmaximur.alcoholtracker.presantation.addmydrink.AddNewDrinkViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddNewDrinkViewModelFactory @Inject constructor(private var addNewDrinkInteractor: AddNewDrinkInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddNewDrinkViewModel(addNewDrinkInteractor)  as T
    }
}