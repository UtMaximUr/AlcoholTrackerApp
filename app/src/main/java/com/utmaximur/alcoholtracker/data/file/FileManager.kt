package com.utmaximur.alcoholtracker.data.file

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import com.utmaximur.alcoholtracker.util.FORMAT_IMAGE
import java.io.*
import java.util.*

class FileManager(private val context: Context) {

    private val sEOF = -1
    private val bufferSize = 1024 * 4

    fun createFile(uri: Uri?): File? {
        if (uri == null) return null
        val contentResolver: ContentResolver = context.contentResolver
        val inputStream: InputStream = contentResolver.openInputStream(uri)!!
        val file: File = createCacheFile()!!
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file)
        } catch (ignored: FileNotFoundException) {
        }
        copy(inputStream, out!!)
        inputStream.close()
        out.close()
        return file
    }

    fun createImageFile(): File? {
        val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            Date().time.toString(),
            FORMAT_IMAGE,
            storageDir
        )
    }

    fun savePhoto(bitmap: Bitmap): String {
        val file = File(context.filesDir, Date().time.toString() + FORMAT_IMAGE)
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
        return file.absolutePath
    }


    private fun createCacheFile(): File? {
        val prefix = "temp"
        val dir = context.filesDir
        var file: File? = null
        try {
            file = File.createTempFile(
                prefix,
                FORMAT_IMAGE,
                dir
            )
        } catch (ignored: IOException) {
        }
        return file
    }

    private fun copy(input: InputStream, output: OutputStream) {
        var n: Int
        val buffer = ByteArray(bufferSize)
        while (sEOF != input.read(buffer).also { n = it }) {
            output.write(buffer, 0, n)
        }
    }
}