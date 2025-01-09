plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
}

dependencies {
    commonMainImplementation(projects.core.decompose)
    commonMainImplementation(projects.features.splashScreen.main.domain)
}