package com.utmaximur.alcoholtracker.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.utmaximur.alcoholtracker.data.file.FileManager
import java.io.File

class FileRepository(private val fileManager: FileManager) {

    fun createFile(context: Context, uri: Uri?): File? {
        return fileManager.createFile(context, uri)
    }

    fun createImageFile(context: Context): File? {
        return fileManager.createImageFile(context)
    }

    fun savePhoto(context: Context, bitmap: Bitmap): String {
        return fileManager.savePhoto(context, bitmap)
    }
}