package com.utmaximur.yandex_map

import android.content.Context
import com.utmaximur.domain.map.PlaceMark
import com.utmaximur.yandex_map.clusters.MapClusterTapListener
import com.utmaximur.yandex_map.clusters.MapClusterViewUpdater
import com.utmaximur.yandex_map.clusters.toCommon
import com.utmaximur.yandex_map.configs.MapSettingsConfig
import com.utmaximur.yandex_map.mapObjects.MapPlaceMarkTapListener
import com.utmaximur.yandex_map.resources.MapIconProvider
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal actual class YandexMapController : KoinComponent {

    private val mapKit: MapKit = get()
    private val context: Context = get()
    private val mapView: MapView = MapView(context)
    private val map by lazy { mapView.mapWindow.map }
    private lateinit var mapIconProvider: MapIconProvider
    private lateinit var mapSettingsConfig: MapSettingsConfig
    private lateinit var mapPlaceMarkTapListener: MapPlaceMarkTapListener
    private lateinit var mapClusterViewUpdater: MapClusterViewUpdater
    private lateinit var mapClusterTapListener: MapClusterTapListener

    actual fun submitData(places: List<PlaceMark>) {
        if (places.isEmpty()) return
        addMarkersOnMap(places)
        val (latitude, longitude) = calculateAverageCoordinates(places)
        val cameraPoint = Point(latitude, longitude)
        moveTo(cameraPoint, mapSettingsConfig.commonZoomLevel)
    }

    actual fun onStart() {
        mapKit.onStart()
        mapView.onStart()
        map.mapObjects.addTapListener(mapPlaceMarkTapListener)
    }

    actual fun onStop() {
        mapClusterTapListener.release()
        map.mapObjects.removeTapListener(mapPlaceMarkTapListener)
        mapView.onStop()
        mapKit.onStop()
    }

    actual fun applyNightMode() {
        map.isNightModeEnabled = mapSettingsConfig.isDarkTheme
    }

    private fun calculateAverageCoordinates(places: List<PlaceMark>): Pair<Double, Double> {
        val avgLatitude = places.map { it.latitude }.average()
        val avgLongitude = places.map { it.longitude }.average()
        return avgLatitude to avgLongitude
    }

    private fun addMarkersOnMap(places: List<PlaceMark>) {
        val clusterizedCollection = map.mapObjects
            .addClusterizedPlacemarkCollection { cluster ->
                mapClusterTapListener.handle(cluster.toCommon())
                mapClusterViewUpdater.update(cluster.toCommon())
            }
        val addedPlaceMarks = clusterizedCollection
            .addEmptyPlacemarks(places.map { Point(it.latitude, it.longitude) })

        addedPlaceMarks.forEachIndexed { index, placeMark ->
            val placeMarkItem = places[index]
            placeMark.userData = placeMarkItem
            val viewProvider = mapIconProvider.getIcon().native
            placeMark.setView(viewProvider)
        }

        clusterizedCollection.clusterPlacemarks(
            mapSettingsConfig.defaultClusterRadius,
            mapSettingsConfig.defaultMinZoom
        )
    }

    private fun moveTo(point: Point, zoom: Float = mapSettingsConfig.comfortableZoomLevel) {
        val position = CameraPosition(point, zoom, 0.0f, 0.0f)
        val animation = Animation(Animation.Type.SMOOTH, 1f)
        map.move(position, animation, null)
    }

    internal fun getView() = mapView

    actual companion object {
        actual fun create(
            mapIconProvider: MapIconProvider,
            mapPlaceMarkTapListener: MapPlaceMarkTapListener,
            mapClusterViewUpdater: MapClusterViewUpdater,
            mapClusterTapListener: MapClusterTapListener,
            mapSettingsConfig: MapSettingsConfig,
            mapObjectListener: (PlaceMarkIds) -> Boolean,
        ): YandexMapController {
            return YandexMapController().apply {
                this.mapIconProvider = mapIconProvider
                this.mapSettingsConfig = mapSettingsConfig
                this.mapPlaceMarkTapListener = mapPlaceMarkTapListener
                this.mapClusterViewUpdater = mapClusterViewUpdater
                this.mapClusterTapListener = mapClusterTapListener
                this.onStart()
                this.applyNightMode()
            }
        }
    }
}