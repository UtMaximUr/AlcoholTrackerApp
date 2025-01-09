plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.ui.android)
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.bundles.coil)

    commonMainImplementation(projects.features.track.createTrack.main.componentApi)
    commonMainImplementation(projects.features.track.createTrack.main.domain)
    commonMainImplementation(projects.features.track.createTrack.domain)
}

compose.resources {
    packageOfResClass = "createTrack.resources"
}