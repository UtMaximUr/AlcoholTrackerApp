plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
}

dependencies {
    commonMainImplementation(projects.coreData.local.trackManager.api)
    commonMainImplementation(projects.coreData.settingsManager.api)
    commonMainImplementation(projects.core.base)
    commonMainImplementation(projects.core.utils)
    commonMainImplementation(projects.features.calendar.domain)
}