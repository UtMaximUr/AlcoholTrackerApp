plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines.core)

    commonMainImplementation(projects.core.base)
    commonMainApi(projects.domain)
}