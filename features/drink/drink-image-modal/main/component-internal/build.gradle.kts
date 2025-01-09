plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
}

dependencies {
    commonMainImplementation(compose.material3)
    commonMainImplementation(compose.components.resources)

    commonMainImplementation(projects.features.drink.drinkImageModal.main.componentApi)
    commonMainImplementation(projects.features.drink.drinkImageModal.main.domain)
    commonMainImplementation(projects.core.permission)
    commonMainImplementation(projects.core.media.compose)
}

compose.resources {
    packageOfResClass = "actions.resources"
}