package com.utmaximur.yandex_map.resources

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.UIKit.NSTextAlignmentCenter
import platform.UIKit.UIColor
import platform.UIKit.UIFont
import platform.UIKit.UIGraphicsImageRenderer
import platform.UIKit.UIImage
import platform.UIKit.UIImageView
import platform.UIKit.UILabel
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
internal actual class MapIconProvider actual constructor(
    private val placeMarkImageProvider: ImageProvider,
    private val clusterImageProvider: ImageProvider,
    private val clusterTextColor: Color
) {
    private val size = CGRectMake(0.0, 0.0, 22.0, 22.0)
    private val clusterBackground by lazy { clusterImageProvider.toNative() }

    actual fun getClusterIcon(clusterSize: Int): MapIcon {
        val image = UIImageView(size)
        image.image = clusterBackground

        val text = UILabel(size)
        text.text = "$clusterSize"
        text.textColor = clusterTextColor.toUIColor()
        text.font = UIFont.boldSystemFontOfSize(11.0)
        text.textAlignment = NSTextAlignmentCenter

        image.addSubview(text)
        return rendererUIImage(image)
    }

    actual fun getIcon(): MapIcon {
        val image = UIImageView(size)
        image.image = placeMarkImageProvider.toNative()
        return rendererUIImage(image)
    }

    private fun rendererUIImage(view: UIView): MapIcon {
        val renderer = UIGraphicsImageRenderer(bounds = size)
        return renderer.imageWithActions { rendererContext ->
            if (rendererContext != null) {
                view.layer.renderInContext(rendererContext.CGContext)
            }
        }.toCommon()
    }

    private fun Color.toUIColor(): UIColor {
        val argb = this.toArgb()
        val blue = argb and 0xff
        val green = argb shr 8 and 0xff
        val red = argb shr 16 and 0xff
        val alpha = argb shr 24 and 0xff
        return UIColor(
            red = red / 255.0,
            green = green / 255.0,
            blue = blue / 255.0,
            alpha = alpha / 255.0
        )
    }
}

internal actual class MapIcon(uiImage: UIImage) {
    val native: UIImage = uiImage
}

internal fun UIImage.toCommon() = MapIcon(this)