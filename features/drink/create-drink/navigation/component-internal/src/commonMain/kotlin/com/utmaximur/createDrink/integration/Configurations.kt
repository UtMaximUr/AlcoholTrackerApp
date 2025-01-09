package com.utmaximur.createDrink.integration

import kotlinx.serialization.Serializable

@Serializable
sealed interface Configuration {

    @Serializable
    data object CreateDrinkScreen : Configuration

}

@Serializable
sealed interface ModalConfiguration {

    @Serializable
    data object ImageActionsDialog : ModalConfiguration

}