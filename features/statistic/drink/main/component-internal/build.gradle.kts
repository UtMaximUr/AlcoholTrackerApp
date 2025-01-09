plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.bundles.coil)

    commonMainImplementation(projects.features.statistic.drink.main.componentApi)
    commonMainImplementation(projects.features.statistic.drink.main.domain)
}

compose.resources {
    packageOfResClass = "drinkStatistic.resources"
}