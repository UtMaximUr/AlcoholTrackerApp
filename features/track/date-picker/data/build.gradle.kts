plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
}

dependencies {
    commonMainImplementation(projects.core.base)
    commonMainImplementation(projects.features.track.datePicker.domain)
}