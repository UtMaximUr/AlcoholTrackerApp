package com.utmaximur.alcoholtracker.data.repository

import android.net.Uri
import com.utmaximur.alcoholtracker.data.file.FileManager

class FileRepository(private val fileManager: FileManager) {

    fun createImageUri() = fileManager.createImageUri()

    fun saveImage(uri: Uri?) = fileManager.saveImage(uri)

    fun deleteImage() = fileManager.deleteImage()

}