package com.utmaximur.yandex_map.clusters

import androidx.collection.LruCache
import com.utmaximur.yandex_map.resources.MapIcon
import com.utmaximur.yandex_map.resources.MapIconProvider
import com.yandex.mapkit.map.Cluster

internal actual class MapClusterViewUpdater actual constructor(
    private val mapIconProvider: MapIconProvider
) {
    private val iconCache = LruCache<Int, MapIcon>(10)
    actual fun update(cluster: MapCluster) {
        val nativeCluster = cluster.native
        val size = nativeCluster.placemarks.size
        val icon = iconCache[size] ?: mapIconProvider.getClusterIcon(size).also {
            iconCache.put(size, it)
        }
        nativeCluster.appearance.setView(icon.native)
    }
}

internal actual class MapCluster(cluster: Cluster) {
    val native: Cluster = cluster
}

internal fun Cluster.toCommon() = MapCluster(this)