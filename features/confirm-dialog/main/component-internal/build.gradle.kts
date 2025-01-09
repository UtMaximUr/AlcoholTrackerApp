plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.confirmDialog.main.componentApi)
    commonMainImplementation(projects.features.confirmDialog.main.domain)
}

compose.resources {
    packageOfResClass = "confirmDialog.resources"
}