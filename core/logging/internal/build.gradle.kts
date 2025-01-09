plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.kermit)

    commonMainImplementation(projects.core.base)
    commonMainImplementation(projects.core.logging.api)

    androidMainImplementation(libs.timber)
}