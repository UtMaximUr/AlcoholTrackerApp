package com.utmaximur.alcoholtracker.dagger.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.utmaximur.alcoholtracker.repository.FileRepository
import com.utmaximur.alcoholtracker.ui.dialog.addphoto.AddPhotoViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddPhotoViewModelFactory  @Inject constructor(private var fileRepository: FileRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddPhotoViewModel(fileRepository)  as T
    }
}