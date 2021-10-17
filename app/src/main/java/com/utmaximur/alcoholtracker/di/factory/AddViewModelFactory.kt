package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.domain.interactor.AddTrackInteractor
import com.utmaximur.alcoholtracker.presentation.add.AddViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddViewModelFactory  @Inject constructor(private var addTrackInteractor: AddTrackInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddViewModel(addTrackInteractor)  as T
    }
}