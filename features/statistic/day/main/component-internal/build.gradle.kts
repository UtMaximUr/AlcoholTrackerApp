plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.core.design)
    commonMainImplementation(projects.features.statistic.day.main.componentApi)
    commonMainImplementation(projects.features.statistic.day.main.domain)
    commonMainImplementation(projects.features.statistic.domain)
}