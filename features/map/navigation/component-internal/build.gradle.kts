plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.map.domain)
    commonMainImplementation(projects.features.map.navigation.componentApi)
    commonMainImplementation(projects.features.map.main.componentApi)
    commonMainImplementation(projects.features.track.createTrack.navigation.componentApi)
    commonMainImplementation(projects.features.track.detailTrack.navigation.componentApi)
    commonMainImplementation(projects.features.track.tracksModal.main.componentApi)
}