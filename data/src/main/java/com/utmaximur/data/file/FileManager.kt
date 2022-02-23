package com.utmaximur.data.file

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import com.utmaximur.data.utils.FILE_PROVIDER
import com.utmaximur.data.utils.FORMAT_IMAGE
import com.utmaximur.domain.repository.FileRepository
import java.io.*
import java.util.*
import javax.inject.Inject

class FileManager @Inject constructor(private val context: Context) : FileRepository {

    private var uri: Uri? = null
    private var file: File? = null

    override fun createImageUri(): Uri? {
        uri = createImageFile()?.let { file ->
            FileProvider.getUriForFile(
                context,
                context.packageName + FILE_PROVIDER,
                file
            )
        }
        return uri
    }

    override fun saveImage(uri: Uri?): String? {
        deleteImage()
        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images
                .Media.getBitmap(context.contentResolver, uri ?: this.uri)

        } else {
            val source = ImageDecoder
                .createSource(context.contentResolver, uri ?: this.uri!!)
            ImageDecoder.decodeBitmap(source)
        }
        file = File(context.filesDir, Date().time.toString() + FORMAT_IMAGE)
        try {
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)
            } finally {
                fos?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file?.absolutePath
    }

    private fun createImageFile(): File? {
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            Date().time.toString(),
            FORMAT_IMAGE,
            storageDir
        )
    }

    override fun deleteImage() {
        if (file?.exists() == true) {
            file?.delete()
        }
    }
}