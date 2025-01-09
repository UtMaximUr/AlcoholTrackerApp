package com.utmaximur.tracker

import org.koin.core.annotation.Single
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.data.tracker.AnalyticsTracker

@Single
internal class RealAnalyticsManager(private val tracker: AnalyticsTracker) : AnalyticsManager {
    override suspend fun trackEvent(event: AnalyticsManager.Event) {
        tracker.logEvent(event.eventName, event.params)
    }
}