package com.utmaximur.alcoholtracker.repository

import android.content.Context
import android.net.Uri
import com.utmaximur.alcoholtracker.data.file.FileGenerator
import java.io.File

class FileRepository(private val fileGenerator: FileGenerator) {

    fun createFile(context: Context, uri: Uri?): File? {
        return fileGenerator.createFile(context, uri)
    }

    fun createImageFile(context: Context): File? {
        return fileGenerator.createImageFile(context)
    }
}