package com.utmaximur.alcoholtracker.presentation.dialog.add_photo

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.data.repository.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPhotoViewModel @Inject constructor(private var fileRepository: FileRepository) :
    ViewModel() {

    fun createImageUri() = fileRepository.createImageUri()

    fun saveImage(uri: Uri? = null) = fileRepository.saveImage(uri)

    fun deleteImage() = fileRepository.deleteImage()

}