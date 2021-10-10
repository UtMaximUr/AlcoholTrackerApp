package com.utmaximur.alcoholtracker.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.utmaximur.alcoholtracker.data.file.FileManager
import java.io.File

class FileRepository(private val fileManager: FileManager) {

    fun createFile(uri: Uri?): File? {
        return fileManager.createFile(uri)
    }

    fun createImageFile(): File? {
        return fileManager.createImageFile()
    }

    fun savePhoto(bitmap: Bitmap): String {
        return fileManager.savePhoto(bitmap)
    }
}