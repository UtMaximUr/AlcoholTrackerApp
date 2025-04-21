plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines.core)

    commonMainImplementation(projects.coreData.databaseRoom)
    commonMainImplementation(projects.coreData.models)
    commonMainImplementation(projects.core.utils)
    commonMainImplementation(projects.features.settings.sortingDrinks.domain)
}
