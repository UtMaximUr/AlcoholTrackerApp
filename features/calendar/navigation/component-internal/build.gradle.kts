plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.calendar.domain)
    commonMainImplementation(projects.features.calendar.navigation.componentApi)
    commonMainImplementation(projects.features.calendar.main.componentApi)
    commonMainImplementation(projects.features.track.createTrack.navigation.componentApi)
    commonMainImplementation(projects.features.track.detailTrack.navigation.componentApi)
}