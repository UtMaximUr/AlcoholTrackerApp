plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.reorderable)

    commonMainImplementation(projects.features.settings.sortingDrinks.main.componentApi)
    commonMainImplementation(projects.features.settings.sortingDrinks.main.domain)
    commonMainImplementation(projects.features.settings.sortingDrinks.domain)
}