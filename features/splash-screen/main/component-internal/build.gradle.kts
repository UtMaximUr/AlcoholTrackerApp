plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
}

dependencies {
    commonMainImplementation(compose.material3)
    commonMainImplementation(compose.components.resources)

    commonMainImplementation(projects.features.splashScreen.main.componentApi)
    commonMainImplementation(projects.features.splashScreen.main.domain)
}

compose.resources {
    packageOfResClass = "splashScreen.resources"
}


