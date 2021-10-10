package com.utmaximur.alcoholtracker.presantation.dialog.addphoto

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.data.repository.FileRepository
import java.io.File
import javax.inject.Inject

class AddPhotoViewModel @Inject constructor(private var fileRepository: FileRepository) :
    ViewModel() {

    var photoURI: Uri? = null
    var photoFile: File? = null

    fun getFile(uri: Uri?): File? {
        return fileRepository.createFile(uri)
    }

    fun getImageFile(): File? {
        return fileRepository.createImageFile()
    }

    fun savePhoto(bitmap: Bitmap): String {
        return fileRepository.savePhoto(bitmap)
    }
}