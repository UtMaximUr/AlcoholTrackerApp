plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.ui.android)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.bundles.coil)

    commonMainImplementation(projects.features.drink.createDrink.main.componentApi)
    commonMainImplementation(projects.features.drink.createDrink.main.domain)
    commonMainImplementation(projects.features.drink.createDrink.domain)
}