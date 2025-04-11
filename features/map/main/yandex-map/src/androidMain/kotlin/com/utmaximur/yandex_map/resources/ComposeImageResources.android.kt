package com.utmaximur.yandex_map.resources

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap

internal actual fun ImageBitmap.toImageProvider(): ImageProvider {
    return asAndroidBitmap().toImageProvider()
}

internal fun Bitmap.toImageProvider(): ImageProvider {
    return ImageProvider.fromBitmap(this)
}