plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
}

dependencies {
    commonMainImplementation(libs.arkivanov.decompose)
    commonMainImplementation(projects.core.decompose)

    commonMainImplementation(projects.domain)
    commonMainImplementation(projects.features.track.geocoder.main.domain)
}