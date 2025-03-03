package com.utmaximur.geocoder.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.utmaximur.design.ui.ElevatedCardApp
import com.utmaximur.geocoder.GeocoderComponent
import geocoder.resources.Res
import geocoder.resources.place
import geocoder.resources.place_hint
import org.jetbrains.compose.resources.stringResource


@Composable
internal fun GeocoderUi(
    component: GeocoderComponent
) {
    val state by component.model.collectAsState()

    ElevatedCardApp(
        contentPaddingValues = PaddingValues(12.dp)
    ) {
        SearchTextField(
            title = stringResource(Res.string.place),
            placeholderText = stringResource(Res.string.place_hint),
            textValue = state.query,
            foundValues = state.places,
            searchIndicatorActive = state.searchStarted,
            onValueChange = component::handleQuery,
            onValueSelect = component::handleSelectedPlace
        )
    }
}