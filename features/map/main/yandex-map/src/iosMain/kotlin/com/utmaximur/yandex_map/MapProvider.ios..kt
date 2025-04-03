package com.utmaximur.yandex_map

import cocoapods.YandexMapsMobile.YMKMapKit
import cocoapods.YandexMapsMobile.setApiKey
import cocoapods.YandexMapsMobile.sharedInstance
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
internal fun provideMapKitFactory(): YMKMapKit {
    YMKMapKit.setApiKey(BuildKonfig.MAP_API_KEY)
    return YMKMapKit.sharedInstance()
}