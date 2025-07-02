plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.store5)
    commonMainImplementation(libs.ktorfit.lib)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.coreData.databaseRoom)
    commonMainImplementation(projects.coreData.models)
    commonMainImplementation(projects.core.base)
    commonMainImplementation(projects.core.utils)
    commonMainImplementation(projects.features.splashScreen.domain)
}
