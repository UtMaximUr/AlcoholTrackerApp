package com.utmaximur.analytics.domain

fun interface AnalyticsManager {

    suspend fun trackEvent(event: Event)

    interface Event {
        val eventName: String
        val params: Map<String, Any>? get() = null
    }
}
