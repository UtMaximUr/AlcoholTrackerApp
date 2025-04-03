package com.utmaximur.yandex_map

import cocoapods.YandexMapsMobile.YMKAnimation
import cocoapods.YandexMapsMobile.YMKAnimationType
import cocoapods.YandexMapsMobile.YMKCameraPosition
import cocoapods.YandexMapsMobile.YMKCluster
import cocoapods.YandexMapsMobile.YMKClusterListenerProtocol
import cocoapods.YandexMapsMobile.YMKClusterTapListenerProtocol
import cocoapods.YandexMapsMobile.YMKMapKit
import cocoapods.YandexMapsMobile.YMKMapObject
import cocoapods.YandexMapsMobile.YMKMapObjectTapListenerProtocol
import cocoapods.YandexMapsMobile.YMKMapView
import cocoapods.YandexMapsMobile.YMKPlacemarkMapObject
import cocoapods.YandexMapsMobile.YMKPoint
import com.utmaximur.domain.models.Place
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIColor
import platform.UIKit.UIView


@OptIn(ExperimentalForeignApi::class)
internal class MapController :
    UIView(frame = CGRectMake(.0, .0, .0, .0)),
    YMKClusterListenerProtocol,
    YMKClusterTapListenerProtocol,
    YMKMapObjectTapListenerProtocol {

    private val mapKit: YMKMapKit = provideMapKitFactory()
    private val mapView = YMKMapView()
    private val map by lazy { mapView.mapWindow?.map }
    private var mapObjectListener: ((List<Long>) -> Unit) = { }

    init {
        addSubview(mapView)
        map?.isRotateGesturesEnabled()
    }

    val appMapController = object : AppMapController {
        override fun submitData(places: List<Place>) {
            if (places.isEmpty()) return
            addMarkersOnMap(places)
            // Устанавливаем позицию камеры по усредненным значениям
            val point = places.elementAtOrNull(places.size / 2)?.let {
                YMKPoint.pointWithLatitude(it.latitude, it.longitude)
            }
            moveTo(point, commonZoomLevel)
        }

        override fun setMapObjectListener(mapObjectListener: (List<Long>) -> Unit) {
            map?.mapObjects?.removeTapListenerWithTapListener(this@MapController)
            map?.mapObjects?.addTapListenerWithTapListener(this@MapController)
            this@MapController.mapObjectListener = { mapObjectListener(it) }
        }

        override fun setDarkMode(isDarkMode: Boolean) {
            map?.setNightModeEnabled(isDarkMode)
        }

        override fun onStart() {
            mapKit.onStart()
        }

        override fun onStop() {
            mapObjectListener = {}
            map?.mapObjects?.removeTapListenerWithTapListener(this@MapController)
            mapKit.onStop()
        }
    }

    /**
     * Установить пины кластера на карту
     */
    private fun addMarkersOnMap(
        places: List<Place>,
        clusterRadius: Double = appMapController.defaultClusterRadius,
        minZoom: Int = appMapController.defaultMinZoom
    ) {
        val clusterizedCollection = map?.mapObjects
            ?.addClusterizedPlacemarkCollectionWithClusterListener(this)
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
            placeMark.setIconWithImage(uiImage("$index", UIColor.clearColor))
        }
        clusterizedCollection?.clusterPlacemarksWithClusterRadius(clusterRadius, minZoom.toULong())
    }

    private fun moveTo(point: YMKPoint?, zoom: Float = appMapController.comfortableZoomLevel) {
        if (point == null) return
        val position = YMKCameraPosition.cameraPositionWithTarget(point, zoom, 0f, 0f)
        val animation = YMKAnimation.animationWithType(YMKAnimationType.YMKAnimationTypeSmooth, 2f)
        map?.moveWithCameraPosition(position, animation, null)
    }

    /**
     * Собрать пины в класстер
     */
    override fun onClusterAddedWithCluster(cluster: YMKCluster) {
        cluster.appearance.setIconWithImage(
            uiImage("${cluster.size}")
        )
        cluster.addClusterTapListenerWithClusterTapListener(this)
    }

    /**
     * Действие по тапу на кластер
     */
    override fun onClusterTapWithCluster(cluster: YMKCluster): Boolean {
        val placesIDs = cluster.placemarks
            .map { it as YMKPlacemarkMapObject }
            .map { it.userData }
            .filterIsInstance<Place>()
            .map { it.trackId }
        mapObjectListener(placesIDs)
        return true
    }

    /**
     * Действие на пине
     */
    override fun onMapObjectTapWithMapObject(mapObject: YMKMapObject, point: YMKPoint): Boolean {
        if (mapObject is YMKPlacemarkMapObject) {
            val place = mapObject.userData
            if (place is Place) {
                mapObjectListener(listOf(place.trackId))
            }
        }
        return true
    }
}