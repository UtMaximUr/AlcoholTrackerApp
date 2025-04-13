plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.map.main.componentApi)
    commonMainImplementation(projects.features.map.main.yandexMap)
    commonMainImplementation(projects.features.map.main.domain)
    commonMainImplementation(projects.features.map.domain)
}