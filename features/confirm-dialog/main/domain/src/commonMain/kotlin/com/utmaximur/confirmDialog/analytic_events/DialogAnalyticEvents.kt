package com.utmaximur.confirmDialog.analytic_events

import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.analytics.params.AnalyticsEvents
import com.utmaximur.analytics.params.Param
import com.utmaximur.analytics.params.ParamValues

internal data class OpenScreenEvent(
    override val eventName: String = AnalyticsEvents.OPEN_SCREEN,
    override val params: Map<String, Any> = mapOf(Param.SCREEN_NAME to ParamValues.CONFIRM_DIALOG)
) : AnalyticsManager.Event