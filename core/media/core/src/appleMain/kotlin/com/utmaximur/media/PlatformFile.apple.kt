package com.utmaximur.media

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.refTo
import kotlinx.cinterop.value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import platform.CoreFoundation.CFRelease
import platform.CoreFoundation.CFStringCreateWithCString
import platform.CoreFoundation.kCFAllocatorDefault
import platform.CoreFoundation.kCFStringEncodingUTF8
import platform.CoreServices.UTTypeCopyPreferredTagWithClass
import platform.CoreServices.UTTypeCreatePreferredIdentifierForTag
import platform.CoreServices.kUTTagClassFilenameExtension
import platform.CoreServices.kUTTagClassMIMEType
import platform.Foundation.CFBridgingRelease
import platform.Foundation.NSData
import platform.Foundation.NSDataReadingUncached
import platform.Foundation.NSError
import platform.Foundation.NSURL
import platform.Foundation.NSURLFileSizeKey
import platform.Foundation.dataWithContentsOfURL
import platform.Foundation.lastPathComponent
import platform.Foundation.pathExtension
import platform.posix.memcpy

actual class PlatformFile(
    val nsUrl: NSURL
) {
    actual val name: String = nsUrl.lastPathComponent ?: String()

    actual val path: String? = nsUrl.absoluteString

    actual val uriString: String = nsUrl.absoluteString ?: String()

    actual val mimeType: String? by lazy { nsUrl.getMimeType() }

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun readBytes(): ByteArray = withContext(Dispatchers.IO) {
        memScoped {
            // Start accessing the security scoped resource
            nsUrl.startAccessingSecurityScopedResource()

            // Read the data
            val error: CPointer<ObjCObjectVar<NSError?>> = alloc<ObjCObjectVar<NSError?>>().ptr
            val nsData = NSData.dataWithContentsOfURL(nsUrl, NSDataReadingUncached, error)
                ?: throw IllegalStateException("Failed to read data from $nsUrl. Error: ${error.pointed.value}")

            // Stop accessing the security scoped resource
            nsUrl.stopAccessingSecurityScopedResource()

            // Copy the data to a ByteArray
            ByteArray(nsData.length.toInt()).apply {
                memcpy(this.refTo(0), nsData.bytes, nsData.length)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    actual fun getSize(): Long? {
        memScoped {
            val valuePointer: CPointer<ObjCObjectVar<Any?>> = alloc<ObjCObjectVar<Any?>>().ptr
            val errorPointer: CPointer<ObjCObjectVar<NSError?>> =
                alloc<ObjCObjectVar<NSError?>>().ptr
            nsUrl.getResourceValue(valuePointer, NSURLFileSizeKey, errorPointer)
            return valuePointer.pointed.value as? Long?
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun NSURL.getMimeType(): String? {
        val fileExtension = pathExtension ?: return null

        // Создаем CFString из расширения файла
        val cfFileExtension = CFStringCreateWithCString(
            kCFAllocatorDefault,
            fileExtension,
            kCFStringEncodingUTF8
        ) ?: return null

        // Получаем UTI для данного расширения
        val uti = UTTypeCreatePreferredIdentifierForTag(
            kUTTagClassFilenameExtension,
            cfFileExtension,
            null
        )

        // Получаем MIME-type на основе UTI
        val mimeType = uti?.let {
            UTTypeCopyPreferredTagWithClass(it, kUTTagClassMIMEType)
        }?.let {
            CFBridgingRelease(it) as? String
        }

        // Освобождаем CFString и UTI
        CFRelease(cfFileExtension)
        uti?.let { CFRelease(it) }

        return mimeType
    }
}