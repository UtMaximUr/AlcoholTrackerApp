package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.domain.interactor.AddNewDrinkInteractor
import com.utmaximur.alcoholtracker.presentation.create_my_drink.CreateMyDrinkViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddNewDrinkViewModelFactory @Inject constructor(private var addNewDrinkInteractor: AddNewDrinkInteractor) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CreateMyDrinkViewModel(addNewDrinkInteractor)  as T
    }
}