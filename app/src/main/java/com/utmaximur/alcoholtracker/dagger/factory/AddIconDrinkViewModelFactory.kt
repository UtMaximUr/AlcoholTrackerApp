package com.utmaximur.alcoholtracker.dagger.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.repository.IconRepository
import com.utmaximur.alcoholtracker.ui.dialog.AddIconDrinkViewModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AddIconDrinkViewModelFactory  @Inject constructor(private var iconRepository: IconRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddIconDrinkViewModel(iconRepository)  as T
    }
}
