package com.utmaximur.yandex_map

import android.widget.RelativeLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.utmaximur.domain.map.PlaceMark

@Composable
internal actual fun NativeMapContent(
    places: List<PlaceMark>,
    yandexMapController: YandexMapController
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            RelativeLayout(context).apply {
                addView(yandexMapController.getView())
            }
        },
        update = {
            yandexMapController.submitData(places)
        },
        onRelease = {
            yandexMapController.onStop()
        },
    )
}