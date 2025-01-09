plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.datetime)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.bundles.coil)

    commonMainImplementation(projects.features.calendar.main.componentApi)
    commonMainImplementation(projects.features.calendar.main.domain)
    commonMainImplementation(projects.features.calendar.domain)
}

compose.resources {
    packageOfResClass = "calendar.resources"
}