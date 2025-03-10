plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.domain)
    commonMainImplementation(projects.features.track.geocoder.main.componentApi)
    commonMainImplementation(projects.features.track.geocoder.main.domain)
}

compose.resources {
    packageOfResClass = "geocoder.resources"
}
