plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
}

dependencies {
    commonMainImplementation(projects.coreData.settingsManager.api)
    commonMainImplementation(projects.core.base)
    commonMainImplementation(projects.features.settings.domain)
}