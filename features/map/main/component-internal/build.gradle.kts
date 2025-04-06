plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.map.main.componentApi)
    commonMainImplementation(projects.features.map.main.yandexMap)
    commonMainImplementation(projects.features.map.main.domain)
    commonMainImplementation(projects.features.map.domain)
}

compose.resources {
    packageOfResClass = "map.resources"
}