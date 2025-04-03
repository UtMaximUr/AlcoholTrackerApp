package com.utmaximur.yandex_map

import androidx.compose.runtime.Composable
import com.utmaximur.domain.models.Place

@Composable
expect fun YandexMapContent(
    places: List<Place>,
    isDarkTheme: Boolean,
    mapObjectClick: (List<Long>) -> Unit,
)