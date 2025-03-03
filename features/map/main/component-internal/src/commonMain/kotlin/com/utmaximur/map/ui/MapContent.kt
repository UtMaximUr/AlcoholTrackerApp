package com.utmaximur.map.ui

import androidx.compose.runtime.Composable
import com.utmaximur.domain.models.Place

@Composable
internal expect fun MapContent(
    places: List<Place>,
    isDarkTheme: Boolean,
    mapObjectClick: (List<Long>) -> Unit
)