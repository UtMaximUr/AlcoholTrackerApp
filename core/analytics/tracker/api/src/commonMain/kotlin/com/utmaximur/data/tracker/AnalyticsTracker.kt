package com.utmaximur.data.tracker

interface AnalyticsTracker {
    suspend fun logEvent(name: String, params: Map<String, Any>? = null)
}