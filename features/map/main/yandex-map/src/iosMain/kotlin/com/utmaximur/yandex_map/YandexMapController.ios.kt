package com.utmaximur.yandex_map

import cocoapods.YandexMapsMobile.YMKAnimation
import cocoapods.YandexMapsMobile.YMKAnimationType
import cocoapods.YandexMapsMobile.YMKCameraPosition
import cocoapods.YandexMapsMobile.YMKMapKit
import cocoapods.YandexMapsMobile.YMKMapView
import cocoapods.YandexMapsMobile.YMKPlacemarkMapObject
import cocoapods.YandexMapsMobile.YMKPoint
import com.utmaximur.domain.map.PlaceMark
import com.utmaximur.yandex_map.clusters.MapClusterListener
import com.utmaximur.yandex_map.clusters.MapClusterTapListener
import com.utmaximur.yandex_map.clusters.MapClusterViewUpdater
import com.utmaximur.yandex_map.clusters.toCommon
import com.utmaximur.yandex_map.configs.MapSettingsConfig
import com.utmaximur.yandex_map.mapObjects.MapPlaceMarkTapListener
import com.utmaximur.yandex_map.resources.MapIconProvider
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
internal actual class YandexMapController {

    private val mapKit: YMKMapKit = provideMapKitFactory()
    private val mapView = YMKMapView()
    private val map by lazy { mapView.mapWindow?.map }
    private lateinit var mapIconProvider: MapIconProvider
    private lateinit var mapSettingsConfig: MapSettingsConfig
    private lateinit var mapPlaceMarkTapListener: MapPlaceMarkTapListener
    private lateinit var mapClusterViewUpdater: MapClusterViewUpdater
    private lateinit var mapClusterTapListener: MapClusterTapListener

    actual fun submitData(places: List<PlaceMark>) {
        if (places.isEmpty()) return
        addMarkersOnMap(places)
        val (latitude, longitude) = calculateAverageCoordinates(places)
        val cameraPoint = YMKPoint.pointWithLatitude(latitude, longitude)
        moveTo(cameraPoint, mapSettingsConfig.commonZoomLevel)
    }

    actual fun applyNightMode() {
        map?.nightModeEnabled = mapSettingsConfig.isDarkTheme
    }

    actual fun onStart() {
        map?.mapObjects?.addTapListenerWithTapListener(mapPlaceMarkTapListener)
        mapKit.onStart()
    }

    actual fun onStop() {
        mapClusterTapListener.release()
        map?.mapObjects?.removeTapListenerWithTapListener(mapPlaceMarkTapListener)
        mapKit.onStop()
    }

    internal fun getView() = mapView

    private fun addMarkersOnMap(
        places: List<PlaceMark>,
        clusterRadius: Double = mapSettingsConfig.defaultClusterRadius,
        minZoom: Int = mapSettingsConfig.defaultMinZoom
    ) {
        val clusterizedCollection = map?.mapObjects
            ?.addClusterizedPlacemarkCollectionWithClusterListener(
                MapClusterListener { cluster ->
                    mapClusterTapListener.handle(cluster.toCommon())
                    mapClusterViewUpdater.update(cluster.toCommon())
                }
            )
        val addedPlaceMarks: List<YMKPlacemarkMapObject>? = clusterizedCollection
            ?.addEmptyPlacemarksWithPoints(
                places.map {
                    YMKPoint.pointWithLatitude(
                        it.latitude,
                        it.longitude
                    )
                }
            )?.filterIsInstance<YMKPlacemarkMapObject>()

        addedPlaceMarks?.forEachIndexed { index, placeMark ->
            val placeMarkItem = places[index]
            placeMark.userData = placeMarkItem
            val uiImage = mapIconProvider.getIcon().native
            placeMark.setIconWithImage(uiImage)
        }
        clusterizedCollection?.clusterPlacemarksWithClusterRadius(clusterRadius, minZoom.toULong())
    }

    private fun moveTo(point: YMKPoint, zoom: Float = mapSettingsConfig.comfortableZoomLevel) {
        val position = YMKCameraPosition.cameraPositionWithTarget(point, zoom, 0f, 0f)
        val animation = YMKAnimation.animationWithType(YMKAnimationType.YMKAnimationTypeSmooth, 2f)
        map?.moveWithCameraPosition(position, animation, null)
    }

    private fun calculateAverageCoordinates(places: List<PlaceMark>): Pair<Double, Double> {
        val avgLatitude = places.map { it.latitude }.average()
        val avgLongitude = places.map { it.longitude }.average()
        return avgLatitude to avgLongitude
    }

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