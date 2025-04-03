package com.utmaximur.yandex_map

import android.content.Context
import com.utmaximur.yandex_map.BuildKonfig
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import org.koin.core.annotation.Single

@Single
internal fun provideMapKitFactory(context: Context): MapKit {
    MapKitFactory.setApiKey(BuildKonfig.MAP_API_KEY)
    MapKitFactory.initialize(context)
    return MapKitFactory.getInstance()
}
