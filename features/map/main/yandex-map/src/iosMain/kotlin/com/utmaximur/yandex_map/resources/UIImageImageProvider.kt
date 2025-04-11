package com.utmaximur.yandex_map.resources

import platform.UIKit.UIImage

internal class UIImageImageProvider internal constructor(private val uiImage: UIImage) : ImageProvider {
    override fun toNative(): UIImage {
        return uiImage
    }
}