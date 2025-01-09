plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.settings.main.componentApi)
    commonMainImplementation(projects.features.settings.main.domain)
    commonMainImplementation(projects.features.settings.domain)
}

compose.resources {
    packageOfResClass = "settings.resources"
}