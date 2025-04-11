package com.utmaximur.yandex_map.resources

import com.yandex.runtime.image.ImageProvider as NativeImageProvider

internal actual interface ImageProvider {

    fun toNative(): NativeImageProvider

    companion object

}