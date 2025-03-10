package com.utmaximur.map.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.utmaximur.domain.models.Place

@Composable
internal actual fun MapContent(
    places: List<Place>,
    isDarkTheme: Boolean,
    mapObjectClick: (List<Long>) -> Unit,
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val mapView = MapController(context)
            mapView.onStart()
            mapView
        },
        update = { mapView ->
            mapView.setDarkMode(isDarkTheme)
            mapView.submitData(places)
            mapView.setMapObjectListener(mapObjectClick)
        },
        onRelease = { mapView ->
            mapView.onStop()
        },
    )
}
