package com.utmaximur.widget

internal expect class NativeAppWidget {
    suspend fun update()
}