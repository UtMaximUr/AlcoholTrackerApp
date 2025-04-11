package com.utmaximur.yandex_map.clusters

import cocoapods.YandexMapsMobile.YMKCluster
import cocoapods.YandexMapsMobile.YMKClusterListenerProtocol
import kotlinx.cinterop.ExperimentalForeignApi
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
internal class MapClusterListener(
    private val onClusterAdded: ((YMKCluster) -> Unit)? = null
): YMKClusterListenerProtocol, NSObject() {
    override fun onClusterAddedWithCluster(cluster: YMKCluster) {
        onClusterAdded?.invoke(cluster)
    }
}