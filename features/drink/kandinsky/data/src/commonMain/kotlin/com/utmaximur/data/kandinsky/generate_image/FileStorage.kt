package com.utmaximur.data.kandinsky.generate_image

import org.koin.core.annotation.Factory
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Factory
internal expect class FileStorage {
    fun saveFileToCache(fileName: String, data: ByteArray): String?
}

@OptIn(ExperimentalEncodingApi::class)
internal fun String.decodeBase64ToByteArray(): ByteArray {
    val byteArray = encodeToByteArray()
    return Base64.decode(byteArray, 0, byteArray.size)
}