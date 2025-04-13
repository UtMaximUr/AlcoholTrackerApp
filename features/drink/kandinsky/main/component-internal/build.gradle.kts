plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
}

dependencies {
    commonMainImplementation(libs.bundles.coil)

    commonMainImplementation(projects.features.drink.kandinsky.main.componentApi)
    commonMainImplementation(projects.features.drink.kandinsky.main.domain)
    commonMainImplementation(projects.features.drink.kandinsky.domain)
}