package com.utmaximur.widget

import com.arkivanov.essenty.lifecycle.LifecycleOwner

interface AppWidgetComponent: LifecycleOwner {

    suspend fun updateAll()
}