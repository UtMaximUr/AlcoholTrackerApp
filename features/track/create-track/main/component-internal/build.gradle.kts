plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.track.createTrack.main.componentApi)
    commonMainImplementation(projects.features.track.createTrack.main.domain)
    commonMainImplementation(projects.features.track.createTrack.domain)
    commonMainImplementation(projects.features.track.geocoder.main.componentApi)
    commonMainImplementation(projects.features.root.bottombar)
    commonMainImplementation(projects.features.appWidget.component)
}