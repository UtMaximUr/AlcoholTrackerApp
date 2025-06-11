package com.utmaximur.widget

import android.content.Context
import androidx.glance.appwidget.updateAll
import org.koin.core.annotation.Factory

@Factory
internal actual class NativeAppWidget(
    private val context: Context
) {

    actual suspend fun update() {
        AppWidget().updateAll(context)
    }
}