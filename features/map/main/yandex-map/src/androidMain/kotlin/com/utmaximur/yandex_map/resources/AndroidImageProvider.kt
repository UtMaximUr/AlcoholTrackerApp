package com.utmaximur.yandex_map.resources

import android.graphics.Bitmap
import com.yandex.runtime.image.ImageProvider as NativeImageProvider

internal class AndroidImageProvider internal constructor(
    private val nativeImageProvider: NativeImageProvider,
) : ImageProvider {
    override fun toNative(): NativeImageProvider {
        return nativeImageProvider
    }
}

internal fun NativeImageProvider.toCommon(): ImageProvider {
    return AndroidImageProvider(this)
}

internal fun ImageProvider.Companion.fromBitmap(bitmap: Bitmap): ImageProvider {
    return NativeImageProvider.fromBitmap(bitmap).toCommon()
}