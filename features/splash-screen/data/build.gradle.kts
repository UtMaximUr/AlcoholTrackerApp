plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
    alias(libs.plugins.custom.ktorfit.plugin)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.store5)
    commonMainImplementation(libs.ktorfit.lib)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.coreData.local.drinkManager.api)
    commonMainImplementation(projects.core.base)
    commonMainImplementation(projects.core.utils)
    commonMainImplementation(projects.domain)
    commonMainImplementation(projects.features.splashScreen.domain)
}