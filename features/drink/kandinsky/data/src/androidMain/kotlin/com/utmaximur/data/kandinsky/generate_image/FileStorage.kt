package com.utmaximur.data.kandinsky.generate_image

import android.content.Context
import org.koin.core.annotation.Factory
import java.io.File

@Factory
internal actual class FileStorage(private val context: Context) {
    actual fun saveFileToCache(fileName: String, data: ByteArray): String? {
        val file = File(context.cacheDir, fileName).apply {
            writeBytes(data)
        }
        return file.absolutePath
    }
}