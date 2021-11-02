package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.data.repository.DataRepository
import com.utmaximur.alcoholtracker.data.repository.FileRepository
import com.utmaximur.alcoholtracker.presentation.dialog.add_photo.AddPhotoViewModel
import com.utmaximur.alcoholtracker.presentation.settings.DataViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataViewModelFactory  @Inject constructor(private val dataRepository: DataRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DataViewModel(dataRepository)  as T
    }
}