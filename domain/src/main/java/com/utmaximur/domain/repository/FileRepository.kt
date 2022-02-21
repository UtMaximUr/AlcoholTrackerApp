package com.utmaximur.domain.repository

import android.net.Uri

interface FileRepository {
    fun createImageUri(): Uri?
    fun saveImage(uri: Uri?): String?
    fun deleteImage()
}