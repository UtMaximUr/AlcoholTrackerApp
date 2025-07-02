plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
}

dependencies {
    commonMainImplementation(projects.features.drink.drinkImageModal.main.componentApi)
    commonMainImplementation(projects.features.drink.drinkImageModal.main.domain)
    commonMainImplementation(projects.core.permission)
    commonMainImplementation(projects.core.media.compose)
}
