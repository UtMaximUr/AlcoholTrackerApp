package com.utmaximur.root.integration

import kotlinx.serialization.Serializable

@Serializable
internal sealed class Configuration {

    @Serializable
    data object SplashScreen : Configuration()

    @Serializable
    data object CalendarScreen : Configuration()

    @Serializable
    data object StatisticScreen : Configuration()

    @Serializable
    data object SettingsScreen : Configuration()
}