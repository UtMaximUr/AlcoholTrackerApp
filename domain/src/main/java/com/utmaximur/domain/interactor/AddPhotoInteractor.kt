package com.utmaximur.domain.interactor


import android.net.Uri
import com.utmaximur.domain.repository.FileRepository
import javax.inject.Inject

class AddPhotoInteractor @Inject constructor(private val fileRepository: FileRepository) {

    fun createImageUri() = fileRepository.createImageUri()

    fun saveImage(uri: Uri?) = fileRepository.saveImage(uri)

    fun deleteImage() = fileRepository.deleteImage()
}