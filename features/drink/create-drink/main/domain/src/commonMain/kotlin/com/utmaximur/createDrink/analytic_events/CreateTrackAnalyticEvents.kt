package com.utmaximur.createDrink.analytic_events

import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.analytics.params.AnalyticsEvents
import com.utmaximur.analytics.params.Param
import com.utmaximur.analytics.params.ParamValues

internal data class OpenScreenEvent(
    override val eventName: String = AnalyticsEvents.OPEN_SCREEN,
    override val params: Map<String, Any> = mapOf(Param.SCREEN_NAME to ParamValues.CREATE_DRINK_SCREEN)
) : AnalyticsManager.Event

internal data class SaveDrinkEvent(
    val drinkName: String,
    override val eventName: String = AnalyticsEvents.CREATE_DRINK,
    override val params: Map<String, Any> = mapOf(Param.DRINK_NAME to drinkName)
) : AnalyticsManager.Event