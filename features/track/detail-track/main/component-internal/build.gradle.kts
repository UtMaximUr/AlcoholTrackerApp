plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.bundles.coil)

    commonMainImplementation(projects.features.track.detailTrack.main.componentApi)
    commonMainImplementation(projects.features.track.detailTrack.main.domain)
    commonMainImplementation(projects.features.track.detailTrack.domain)
    commonMainImplementation(projects.features.track.geocoder.main.componentApi)
    commonMainImplementation(projects.features.root.bottombar)
}