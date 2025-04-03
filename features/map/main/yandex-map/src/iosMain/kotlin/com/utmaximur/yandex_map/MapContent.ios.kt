package com.utmaximur.yandex_map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import com.utmaximur.domain.models.Place

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun YandexMapContent(
    places: List<Place>,
    isDarkTheme: Boolean,
    mapObjectClick: (List<Long>) -> Unit,
) {
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            val mapView = MapController()
            mapView.appMapController.onStart()
            mapView
        },
        update = { mapView ->
            mapView.appMapController.setDarkMode(isDarkTheme)
            mapView.appMapController.submitData(places)
            mapView.appMapController.setMapObjectListener(mapObjectClick)
        },
        onRelease = { mapView ->
            mapView.appMapController.onStop()
        },
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative,
            isNativeAccessibilityEnabled = true
        )
    )
}
