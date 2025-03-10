package com.utmaximur.map.integration

import kotlinx.serialization.Serializable

@Serializable
sealed interface Configuration {

    @Serializable
    data object MapScreen : Configuration

    @Serializable
    data object CreateTrackScreen : Configuration

    @Serializable
    data class DetailTrackScreen(val trackId: Long) : Configuration
}

@Serializable
sealed interface ModalConfiguration {

    @Serializable
    data class TracksDialog(val trackIds: List<Long>) : ModalConfiguration
}
