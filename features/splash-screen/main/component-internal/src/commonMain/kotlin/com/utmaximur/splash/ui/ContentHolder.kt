package com.utmaximur.splash.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.utmaximur.splash.ui.wave.WaveView


@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ContentHolder(
    innerPadding: PaddingValues,
    content: @Composable BoxScope.() -> Unit = { }
) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        WaveView()
        content()
    }
}