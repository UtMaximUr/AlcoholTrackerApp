plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
}

dependencies {
    commonMainImplementation(compose.material3)
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.bundles.coil)

    commonMainImplementation(projects.features.drink.kandinsky.main.componentApi)
    commonMainImplementation(projects.features.drink.kandinsky.main.domain)
    commonMainImplementation(projects.features.drink.kandinsky.domain)
}

compose.resources {
    packageOfResClass = "kandinsky.resources"
}
