plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.datetime)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.bundles.coil)

    commonMainImplementation(projects.features.calendar.main.componentApi)
    commonMainImplementation(projects.features.calendar.main.domain)
    commonMainImplementation(projects.features.calendar.domain)
}