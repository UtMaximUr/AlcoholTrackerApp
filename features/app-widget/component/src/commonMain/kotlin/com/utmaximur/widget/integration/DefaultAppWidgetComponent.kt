package com.utmaximur.widget.integration

import com.arkivanov.decompose.ComponentContext
import com.utmaximur.widget.AppWidgetComponent
import com.utmaximur.widget.NativeAppWidget
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Factory
internal class DefaultAppWidgetComponent(
    @InjectedParam componentContext: ComponentContext,
) : AppWidgetComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val nativeAppWidget: NativeAppWidget by inject()

    override suspend fun updateAll() {
        nativeAppWidget.update()
    }
}