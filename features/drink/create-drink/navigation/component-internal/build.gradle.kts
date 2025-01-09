plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.drink.createDrink.navigation.componentApi)
    commonMainImplementation(projects.features.drink.createDrink.main.componentApi)
    commonMainImplementation(projects.features.drink.drinkImageModal.main.componentApi)
}