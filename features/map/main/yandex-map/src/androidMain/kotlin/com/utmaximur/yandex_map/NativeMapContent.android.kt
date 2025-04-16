package com.utmaximur.yandex_map

import android.widget.RelativeLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
internal actual fun NativeMapContent(
    yandexMapController: YandexMapController
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            RelativeLayout(context).apply {
                addView(yandexMapController.getMapView().mapView)
            }
        },
        onRelease = {
            yandexMapController.onStop()
        },
    )
}