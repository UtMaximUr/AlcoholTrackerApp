plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
}

dependencies {
    commonMainImplementation(libs.androidx.datastore.preferences)

    commonMainImplementation(projects.core.base)
    commonMainImplementation(projects.coreData.settingsManager.api)
}