package com.utmaximur.yandex_map.clusters

import cocoapods.YandexMapsMobile.YMKCluster
import cocoapods.YandexMapsMobile.YMKClusterTapListenerProtocol
import cocoapods.YandexMapsMobile.YMKPlacemarkMapObject
import com.utmaximur.domain.map.PlaceMark
import com.utmaximur.yandex_map.PlaceMarkIds
import kotlinx.cinterop.ExperimentalForeignApi
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
internal actual class MapClusterTapListener
actual constructor(private val onClusterTap: ((PlaceMarkIds) -> Boolean)?) {
    private val tapListeners = mutableMapOf<YMKCluster, YMKClusterTapListenerProtocol>()

    actual fun handle(cluster: MapCluster) {
        val nativeCluster = cluster.getNative()
        val tapListener = object : YMKClusterTapListenerProtocol, NSObject() {
            override fun onClusterTapWithCluster(cluster: YMKCluster): Boolean {
                return onClusterTap?.invoke(extractTrackIds(cluster)) ?: false
            }
        }
        nativeCluster.addClusterTapListenerWithClusterTapListener(tapListener)
        tapListeners[nativeCluster] = tapListener
    }

    actual fun release() {
        for ((cluster, clusterTapListener) in tapListeners) {
            cluster.removeClusterTapListenerWithClusterTapListener(clusterTapListener)
        }
        tapListeners.clear()
    }

    private fun extractTrackIds(cluster: YMKCluster): PlaceMarkIds {
        return cluster.placemarks
            .map { it as YMKPlacemarkMapObject }
            .mapNotNull { it.userData as? PlaceMark }
            .map { it.trackId }
    }
}