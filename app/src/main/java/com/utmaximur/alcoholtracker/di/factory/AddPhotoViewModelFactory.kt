package com.utmaximur.alcoholtracker.di.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.data.repository.FileRepository
import com.utmaximur.alcoholtracker.presantation.dialog.addphoto.AddPhotoViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddPhotoViewModelFactory  @Inject constructor(private var fileRepository: FileRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddPhotoViewModel(fileRepository)  as T
    }
}