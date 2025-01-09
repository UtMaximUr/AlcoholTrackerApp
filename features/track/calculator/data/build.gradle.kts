plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
}

dependencies {
    commonMainImplementation(libs.multiplatform.expressio.evaluator)
    commonMainImplementation(projects.core.base)
    commonMainImplementation(projects.features.track.calculator.domain)
}