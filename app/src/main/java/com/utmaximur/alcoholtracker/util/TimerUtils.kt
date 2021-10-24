package com.utmaximur.alcoholtracker.util

import androidx.lifecycle.findViewTreeLifecycleOwner
import android.view.View
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.*

fun View.delayOnLifeCycle(
    durationInMillis: Long,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    block: () -> Unit
): Job? = findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
    lifecycleOwner.lifecycle.coroutineScope.launch(dispatcher) {
        delay(durationInMillis)
        block()
    }
}

