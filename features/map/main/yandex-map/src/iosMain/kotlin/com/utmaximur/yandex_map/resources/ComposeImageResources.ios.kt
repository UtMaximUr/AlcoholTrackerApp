package com.utmaximur.yandex_map.resources

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGBitmapContextCreateImage
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGImageAlphaInfo
import platform.CoreGraphics.kCGBitmapByteOrder32Little
import platform.UIKit.UIImage

internal actual fun ImageBitmap.toImageProvider(): ImageProvider {
    return ImageProvider.fromUIImage(toUIImage())
}

@OptIn(ExperimentalForeignApi::class)
internal fun ImageBitmap.toUIImage(): UIImage {
    val width = this.width
    val height = this.height
    val buffer = IntArray(width * height)

    this.readPixels(buffer)

    val colorSpace = CGColorSpaceCreateDeviceRGB()
    val bitmapInfo =
        CGImageAlphaInfo.kCGImageAlphaPremultipliedFirst.value or kCGBitmapByteOrder32Little
    val context = CGBitmapContextCreate(
        data = buffer.refTo(0),
        width = width.toULong(),
        height = height.toULong(),
        bitsPerComponent = 8u,
        bytesPerRow = (4 * width).toULong(),
        space = colorSpace,
        bitmapInfo = bitmapInfo
    )

    val cgImage = CGBitmapContextCreateImage(context)
    return cgImage.let { UIImage.imageWithCGImage(it) }
}