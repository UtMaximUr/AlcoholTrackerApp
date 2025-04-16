package com.utmaximur.yandex_map.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun MapBox(
    mapContent: @Composable () -> Unit,
    mapControlContent: @Composable () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        mapContent()
        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(16.dp)
        ) {
            mapControlContent()
        }
    }
}