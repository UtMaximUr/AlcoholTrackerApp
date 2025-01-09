package com.utmaximur.calculator.analytic_events

import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.analytics.params.AnalyticsEvents
import com.utmaximur.analytics.params.Param

internal data class OpenScreenEvent(
    override val eventName: String = AnalyticsEvents.OPEN_CALCULATOR_DIALOG
) : AnalyticsManager.Event