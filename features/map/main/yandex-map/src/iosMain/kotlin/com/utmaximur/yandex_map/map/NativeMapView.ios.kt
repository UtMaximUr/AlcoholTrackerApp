package com.utmaximur.yandex_map.map

import cocoapods.YandexMapsMobile.YMKMapView
import kotlinx.cinterop.ExperimentalForeignApi


@OptIn(ExperimentalForeignApi::class)
internal actual class NativeMapView(val mapView: YMKMapView)