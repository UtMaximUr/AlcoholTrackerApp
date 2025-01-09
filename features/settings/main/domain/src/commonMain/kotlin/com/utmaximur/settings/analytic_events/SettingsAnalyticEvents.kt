package com.utmaximur.settings.analytic_events

import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.analytics.params.AnalyticsEvents
import com.utmaximur.analytics.params.Param
import com.utmaximur.analytics.params.ParamValues

internal data class OpenScreenEvent(
    override val eventName: String = AnalyticsEvents.OPEN_SCREEN,
    override val params: Map<String, Any> = mapOf(Param.SCREEN_NAME to ParamValues.SETTINGS_SCREEN)
) : AnalyticsManager.Event

internal data class ChangeThemeEvent(
    val isDarkTheme: Boolean,
    override val eventName: String = AnalyticsEvents.OPEN_SCREEN,
    override val params: Map<String, Any> = mapOf(Param.IS_DARK_THEME to isDarkTheme)
) : AnalyticsManager.Event

internal data class OpenCurrencyDialogEvent(
    override val eventName: String = AnalyticsEvents.OPEN_CURRENCY_DIALOG
) : AnalyticsManager.Event

internal data class OpenPrivacyEvent(
    override val eventName: String = AnalyticsEvents.OPEN_PRIVACY
) : AnalyticsManager.Event

internal data class OpenTermsEvent(
    override val eventName: String = AnalyticsEvents.OPEN_TERMS,
) : AnalyticsManager.Event