package com.utmaximur.createTrack.integration

import kotlinx.serialization.Serializable

@Serializable
sealed interface Configuration {

    @Serializable
    data object CreateTrackScreen : Configuration

    @Serializable
    data object CreateDrinkScreen : Configuration

}

@Serializable
sealed interface ModalConfiguration {

    @Serializable
    data object CalculatorDialog : ModalConfiguration

    @Serializable
    data object CurrencyDialog : ModalConfiguration

    @Serializable
    data class DatePickerDialog(val selectedDate: Long?) : ModalConfiguration
}