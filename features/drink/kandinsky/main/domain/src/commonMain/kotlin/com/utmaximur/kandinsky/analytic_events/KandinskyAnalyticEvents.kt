package com.utmaximur.kandinsky.analytic_events

import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.analytics.params.AnalyticsEvents
import com.utmaximur.analytics.params.Param
import com.utmaximur.analytics.params.ParamValues

internal data class OpenScreenEvent(
    override val eventName: String = AnalyticsEvents.OPEN_SCREEN,
    override val params: Map<String, Any> = mapOf(Param.SCREEN_NAME to ParamValues.KANDINSKY_SCREEN)
) : AnalyticsManager.Event

internal data class GenerationImageEvent(
    val prompt: String,
    val style: String,
    override val eventName: String = AnalyticsEvents.GENERATION_IMAGE,
    override val params: Map<String, Any> = mapOf(
        Param.PROMPT to prompt,
        Param.STYLE to style,
    )
) : AnalyticsManager.Event