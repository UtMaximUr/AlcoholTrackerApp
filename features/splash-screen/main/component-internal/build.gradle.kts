plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
}

dependencies {
    commonMainImplementation(projects.features.splashScreen.main.componentApi)
    commonMainImplementation(projects.features.splashScreen.main.domain)
    commonMainImplementation(projects.features.root.bottombar)
}