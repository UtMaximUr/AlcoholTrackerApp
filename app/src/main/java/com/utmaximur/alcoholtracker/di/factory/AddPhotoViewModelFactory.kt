package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.data.repository.FileRepository
import com.utmaximur.alcoholtracker.presentation.dialog.add_photo.AddPhotoViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddPhotoViewModelFactory  @Inject constructor(private var fileRepository: FileRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddPhotoViewModel(fileRepository)  as T
    }
}