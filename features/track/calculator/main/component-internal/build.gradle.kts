plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.track.calculator.main.componentApi)
    commonMainImplementation(projects.features.track.calculator.main.domain)
}

compose.resources {
    packageOfResClass = "calculator.resources"
}