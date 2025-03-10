package com.utmaximur.tracker

import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.data.tracker.AnalyticsTracker
import org.koin.core.annotation.Single

@Single
internal class RealAnalyticsManager(private val tracker: AnalyticsTracker) : AnalyticsManager {
    override suspend fun trackEvent(event: AnalyticsManager.Event) {
        tracker.logEvent(event.eventName, event.params)
    }
}
