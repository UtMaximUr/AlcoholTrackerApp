package com.utmaximur.crm_tracker

import com.utmaximur.core.logging.Logger
import org.koin.core.annotation.Single
import com.utmaximur.data.tracker.AnalyticsTracker

@Single
internal class RealAnalyticsTracker(
    private val logger: Logger
) : AnalyticsTracker {

    override suspend fun logEvent(name: String, params: Map<String, Any>?) {
        logger.i { "[Analytic event -> $name _ $params]" }
    }
}