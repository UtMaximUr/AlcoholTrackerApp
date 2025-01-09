plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.statistic.day.main.componentApi)
    commonMainImplementation(projects.features.statistic.day.main.domain)
    commonMainImplementation(projects.features.statistic.domain)
}

compose.resources {
    packageOfResClass = "day.resources"
}