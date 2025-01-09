package com.utmaximur.detailTrack.integration

import kotlinx.serialization.Serializable

@Serializable
sealed interface Configuration {

    @Serializable
    data object DetailTrackScreen : Configuration

}

@Serializable
sealed interface ModalConfiguration {

    @Serializable
    data object CalculatorDialog : ModalConfiguration

    @Serializable
    data class DatePickerDialog(val selectedDate: Long?) : ModalConfiguration

    @Serializable
    data class ConfirmDialog(val trackId: Long) : ModalConfiguration
}