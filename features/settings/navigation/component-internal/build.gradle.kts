plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(projects.features.settings.domain)

    commonMainImplementation(projects.features.settings.navigation.componentApi)
    commonMainImplementation(projects.features.settings.main.componentApi)
    commonMainImplementation(projects.features.settings.currencyModal.componentApi)
}