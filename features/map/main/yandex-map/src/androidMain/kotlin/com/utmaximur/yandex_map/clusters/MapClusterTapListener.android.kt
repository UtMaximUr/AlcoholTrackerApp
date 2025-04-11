package com.utmaximur.yandex_map.clusters

import com.utmaximur.domain.map.PlaceMark
import com.utmaximur.yandex_map.PlaceMarkIds
import com.yandex.mapkit.map.Cluster
import com.yandex.mapkit.map.ClusterTapListener

internal actual class MapClusterTapListener
actual constructor(private val onClusterTap: ((PlaceMarkIds) -> Boolean)?) {
    private val tapListeners = mutableMapOf<Cluster, ClusterTapListener>()
    actual fun handle(cluster: MapCluster) {
        val nativeCluster = cluster.native
        val tapListener = ClusterTapListener {
            onClusterTap?.invoke(extractTrackIds(nativeCluster)) ?: false
        }
        nativeCluster.addClusterTapListener(tapListener)
        tapListeners[nativeCluster] = tapListener
    }

    actual fun release() {
        for ((cluster, clusterTapListener) in tapListeners) {
            cluster.removeClusterTapListener(clusterTapListener)
        }
        tapListeners.clear()
    }

    private fun extractTrackIds(cluster: Cluster): PlaceMarkIds {
        return cluster.placemarks
            .mapNotNull { it.userData as? PlaceMark }
            .map { it.trackId }
    }
}