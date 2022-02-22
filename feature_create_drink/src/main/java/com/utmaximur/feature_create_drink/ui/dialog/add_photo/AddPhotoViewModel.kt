package com.utmaximur.feature_create_drink.ui.dialog.add_photo

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.utmaximur.domain.interactor.AddPhotoInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPhotoViewModel @Inject constructor(private var addPhotoInteractor: AddPhotoInteractor) :
    ViewModel() {

    fun createImageUri() = addPhotoInteractor.createImageUri()

    fun saveImage(uri: Uri? = null) = addPhotoInteractor.saveImage(uri)

    fun deleteImage() = addPhotoInteractor.deleteImage()

}