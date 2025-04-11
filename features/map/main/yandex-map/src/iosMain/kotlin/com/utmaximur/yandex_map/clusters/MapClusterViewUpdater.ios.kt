package com.utmaximur.yandex_map.clusters

import androidx.collection.LruCache
import cocoapods.YandexMapsMobile.YMKCluster
import com.utmaximur.yandex_map.resources.MapIcon
import com.utmaximur.yandex_map.resources.MapIconProvider
import kotlinx.cinterop.ExperimentalForeignApi

internal actual class MapClusterViewUpdater actual constructor(
    private val mapIconProvider: MapIconProvider
) {
    private val iconCache = LruCache<Int, MapIcon>(10)

    @OptIn(ExperimentalForeignApi::class)
    actual fun update(cluster: MapCluster) {
        val nativeCluster = cluster.getNative()
        val size = nativeCluster.placemarks.size
        val icon = iconCache[size] ?: mapIconProvider.getClusterIcon(size).also {
            iconCache.put(size, it)
        }
        nativeCluster.appearance.setIconWithImage(icon.native)
    }
}

@OptIn(ExperimentalForeignApi::class)
internal actual class MapCluster(private val cluster: YMKCluster) {
    fun getNative() = cluster
}

@OptIn(ExperimentalForeignApi::class)
internal fun YMKCluster.toCommon() = MapCluster(this)