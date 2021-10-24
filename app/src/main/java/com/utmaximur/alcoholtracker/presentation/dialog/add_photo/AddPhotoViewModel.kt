package com.utmaximur.alcoholtracker.presentation.dialog.add_photo

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.data.repository.FileRepository
import java.io.File
import javax.inject.Inject

class AddPhotoViewModel @Inject constructor(private var fileRepository: FileRepository) :
    ViewModel() {

    val photoURI: LiveData<Uri> by lazy {
        MutableLiveData()
    }

    val photoFile: LiveData<File> by lazy {
        MutableLiveData()
    }

    fun getFile(uri: Uri?): File? {
        return fileRepository.createFile(uri)
    }

    fun updateImageFile(){
        (photoFile as MutableLiveData).value = fileRepository.createImageFile()
    }

    fun updatePhotoUri(uri: Uri) {
        (photoURI as MutableLiveData).value = uri
    }

    fun savePhoto(bitmap: Bitmap): String {
        return fileRepository.savePhoto(bitmap)
    }

    fun deleteFile() {
        if (photoFile.value != null) {
            fileRepository.deleteFile(photoFile.value!!)
        }
    }

    fun clearFile() {
        (photoFile as MutableLiveData).value = null
    }
}