package com.utmaximur.settings.integration

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface SettingsNavigationConfiguration {
    @Serializable
    data object Main : SettingsNavigationConfiguration

    @Serializable
    data object SortingDrinks : SettingsNavigationConfiguration
}

@Serializable
internal sealed interface ModalConfiguration {
    @Serializable
    data object Currency : ModalConfiguration
}
