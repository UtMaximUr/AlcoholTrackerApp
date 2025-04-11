package com.utmaximur.yandex_map.resources

import platform.UIKit.UIImage

internal actual interface ImageProvider {

    fun toNative(): UIImage

    companion object

}

internal fun ImageProvider.Companion.fromUIImage(uiImage: UIImage): ImageProvider {
    return UIImageImageProvider(uiImage)
}