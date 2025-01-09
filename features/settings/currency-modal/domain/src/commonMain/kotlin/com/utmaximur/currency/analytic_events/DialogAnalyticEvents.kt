package com.utmaximur.currency.analytic_events

import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.analytics.params.AnalyticsEvents
import com.utmaximur.analytics.params.Param

internal data class ChangeCurrencyEvent(
    val currency: String,
    override val eventName: String = AnalyticsEvents.CHANGE_CURRENCY,
    override val params: Map<String, Any> = mapOf(Param.CURRENCY to currency)
) : AnalyticsManager.Event