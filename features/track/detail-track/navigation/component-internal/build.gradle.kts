plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.track.detailTrack.navigation.componentApi)
    commonMainImplementation(projects.features.track.detailTrack.main.componentApi)
    commonMainImplementation(projects.features.track.calculator.main.componentApi)
    commonMainImplementation(projects.features.track.datePicker.main.componentApi)
    commonMainImplementation(projects.features.confirmDialog.main.componentApi)
}