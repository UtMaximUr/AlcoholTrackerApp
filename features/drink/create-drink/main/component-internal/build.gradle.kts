plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.androidx.ui.android)
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.bundles.coil)

    commonMainImplementation(projects.features.drink.createDrink.main.componentApi)
    commonMainImplementation(projects.features.drink.createDrink.main.domain)
    commonMainImplementation(projects.features.drink.createDrink.domain)
}

compose.resources {
    packageOfResClass = "createDrink.resources"
}