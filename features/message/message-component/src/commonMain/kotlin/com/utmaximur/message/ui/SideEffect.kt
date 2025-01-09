package com.utmaximur.message.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.coroutines.withLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

val LocalLifecycleOwner = compositionLocalOf<LifecycleOwner> {
    error("No LocalLifecycleOwner provided")
}

@Composable
fun <T> Flow<T>.collectSideEffectWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEach: @Composable (T) -> Unit
) {
    var sideEffect by remember { mutableStateOf<T?>(null) }

    LaunchedEffect(null) {
        this@collectSideEffectWithLifecycle
            .withLifecycle(lifecycleOwner.lifecycle)
            .onEach {
                sideEffect = it
            }.launchIn(this)
    }

    sideEffect?.let {
        onEach(it)
        sideEffect = null
    }
}