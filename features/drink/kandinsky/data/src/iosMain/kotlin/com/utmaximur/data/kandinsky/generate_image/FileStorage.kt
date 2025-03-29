package com.utmaximur.data.kandinsky.generate_image

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import org.koin.core.annotation.Factory
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.writeToURL

@Factory
internal actual class FileStorage {
    @OptIn(ExperimentalForeignApi::class)
    actual fun saveFileToCache(fileName: String, data: ByteArray): String? {
        val fileURL = NSFileManager.defaultManager
            .URLForDirectory(
                directory = NSCachesDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = true,
                error = null
            )?.URLByAppendingPathComponent(fileName)

        fileURL ?: return null
        byteArrayToData(data).writeToURL(fileURL, true)
        return fileURL.path
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun byteArrayToData(bytes: ByteArray): NSData = memScoped {
    NSData.create(
        bytes = allocArrayOf(bytes),
        length = bytes.size.toULong()
    )
}