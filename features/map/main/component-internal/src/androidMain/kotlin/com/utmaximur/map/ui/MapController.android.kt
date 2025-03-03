package com.utmaximur.map.ui


import android.content.Context
import android.view.Gravity
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.utmaximur.domain.models.Place
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Cluster
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.ClusterTapListener
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import features.map.main.componentinternal.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.get


/**
 * Кастомное вью карты.
 *
 * @constructor
 * создает кастомную вью.
 *
 * @param context
 */

internal class MapController(private val context: Context) : RelativeLayout(context),
    ClusterListener, ClusterTapListener, MapObjectTapListener, KoinComponent {

    private val mapKit: MapKit = get()
    private val mapView: MapView = MapView(context)
    private val map by lazy { mapView.mapWindow.map }
    private var mapObjectListener: (List<Long>) -> Unit = {}

    init {
        addView(mapView)
    }

    /**
     * Установить список объектов на карту
     */
    fun submitData(places: List<Place>) {
        addMarkersOnMap(places)
        // Устанавливаем позицию камеры по усредненным значениям
        val point = places.elementAtOrNull(places.size / 2)?.let {
            Point(it.latitude, it.longitude)
        }
        moveTo(point, COMMON_ZOOM_LEVEL)
    }

    /**
     * Установить слушатель нажатия на объект на карте
     */
    fun setMapObjectListener(mapObjectListener: (List<Long>) -> Unit) {
        this.mapObjectListener = { mapObjectListener(it) }
    }

    /**
     * Устанавливает для карты NightMode
     */
    fun setDarkMode(isDarkMode: Boolean) {
        map.isNightModeEnabled = isDarkMode
    }

    fun onStart() {
        mapKit.onStart()
        mapView.onStart()
        map.mapObjects.addTapListener(this)
    }

    fun onStop() {
        map.mapObjects.removeTapListener(this)
        mapView.onStop()
        mapKit.onStop()
    }

    /**
     * Установить маркеры объектов на карту
     */
    private fun addMarkersOnMap(
        places: List<Place>,
        clusterRadius: Double = DEFAULT_CLUSTER_RADIUS,
        minZoom: Int = DEFAULT_MIN_ZOOM
    ) {
        val clusterizedCollection = map.mapObjects.addClusterizedPlacemarkCollection(this)
        val addedPlaceMarks = clusterizedCollection
            .addEmptyPlacemarks(places.map { Point(it.latitude, it.longitude) })

        addedPlaceMarks.forEachIndexed { index, placeMark ->
            val placeMarkItem = places[index]
            placeMark.userData = placeMarkItem
            getClusterItemIcon().let(placeMark::setView)
        }

        clusterizedCollection.clusterPlacemarks(clusterRadius, minZoom)
    }

    private fun moveTo(point: Point?, zoom: Float = COMFORTABLE_ZOOM_LEVEL) {
        if (point == null) return
        val position = CameraPosition(point, zoom, 0.0f, 0.0f)
        val animation = Animation(Animation.Type.SMOOTH, 1f)
        map.move(position, animation, null)
    }

    /**
     * Установить иконку кластера объектов на карте
     * @param cluster - список объектов на карте
     */
    private fun getClusterIcon(cluster: List<Place>): ViewProvider {
        val textView = TextView(context)
        val clusterSize = cluster.size.toString()
        textView.text = clusterSize
        textView.gravity = Gravity.CENTER
        textView.setBackgroundResource(R.drawable.marker_default_icon)
        return ViewProvider(textView)
    }

    /**
     * Установить иконку кластера объектов на карте
     */
    private fun getClusterItemIcon(): ViewProvider {
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.ic_local_bar_white_24dp)
        return ViewProvider(imageView)
    }

    /**
     * Собрать пины в класстер
     */
    override fun onClusterAdded(cluster: Cluster) {
        val clusterIcon = getClusterIcon(cluster.placemarks.map { it.userData as Place })
        clusterIcon.let(cluster.appearance::setView)
        cluster.addClusterTapListener(this)
    }

    /**
     * Действие по тапу на кластер
     */
    override fun onClusterTap(cluster: Cluster): Boolean {
        val placesIDs = cluster.placemarks
            .map { it.userData }
            .filterIsInstance<Place>()
            .map { it.trackId }
        mapObjectListener(placesIDs)
        return true
    }

    /**
     * Действие на пине
     */
    override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean {
        if (mapObject is PlacemarkMapObject) {
            val place = mapObject.userData
            if (place is Place) {
                mapObjectListener(listOf(place.trackId))
            }
        }
        return true
    }

    private companion object {
        const val DEFAULT_CLUSTER_RADIUS = 42.0
        const val DEFAULT_MIN_ZOOM = 35
        const val COMFORTABLE_ZOOM_LEVEL = 15f
        const val COMMON_ZOOM_LEVEL = 10f
    }
}
