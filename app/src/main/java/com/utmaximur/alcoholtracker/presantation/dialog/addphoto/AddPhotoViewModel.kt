package com.utmaximur.alcoholtracker.presantation.dialog.addphoto

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.data.repository.FileRepository
import java.io.File

class AddPhotoViewModel(private var fileRepository: FileRepository) : ViewModel() {

    var photoURI: Uri? = null
    var photoFile: File? = null

    fun getFile(context: Context, uri: Uri?): File? {
        return fileRepository.createFile(context, uri)
    }

    fun getImageFile(context: Context): File? {
        return fileRepository.createImageFile(context)
    }

    fun savePhoto(context: Context, bitmap: Bitmap): String {
        return fileRepository.savePhoto(context, bitmap)
    }
}