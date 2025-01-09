package com.utmaximur.calendar.integration

import kotlinx.serialization.Serializable

@Serializable
sealed interface Configuration {

    @Serializable
    data object CalendarScreen : Configuration

    @Serializable
    data object CreateTrackScreen : Configuration

    @Serializable
    data class DetailTrackScreen(val trackId: Long) : Configuration

}