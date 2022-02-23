package com.utmaximur.data.repository

import android.net.Uri
import com.utmaximur.data.file.FileManager
import com.utmaximur.domain.repository.FileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRepositoryImpl @Inject constructor(private val fileManager: FileManager) :
    FileRepository {

    override fun createImageUri() = fileManager.createImageUri()

    override fun saveImage(uri: Uri?) = fileManager.saveImage(uri)

    override fun deleteImage() = fileManager.deleteImage()

}