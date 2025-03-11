package com.utmaximur.map.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.utmaximur.domain.models.Place

@Composable
internal actual fun MapContent(
    places: List<Place>,
    isDarkTheme: Boolean,
    mapObjectClick: (List<Long>) -> Unit,
) {

}
