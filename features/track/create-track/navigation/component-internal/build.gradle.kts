plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.track.createTrack.navigation.componentApi)
    commonMainImplementation(projects.features.track.createTrack.main.componentApi)
    commonMainImplementation(projects.features.track.calculator.main.componentApi)
    commonMainImplementation(projects.features.track.datePicker.main.componentApi)
    commonMainImplementation(projects.features.settings.currencyModal.componentApi)
    commonMainImplementation(projects.features.drink.createDrink.navigation.componentApi)
}