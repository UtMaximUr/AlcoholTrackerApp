plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
}

dependencies {
    commonMainImplementation(libs.arkivanov.decompose)

    commonMainImplementation(projects.core.decompose)
    commonMainImplementation(projects.features.track.createTrack.main.domain)
    commonMainImplementation(projects.domain)
}