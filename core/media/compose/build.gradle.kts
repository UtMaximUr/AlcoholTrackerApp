plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.ui)

    commonMainApi(projects.core.media.core)
    androidMainImplementation(libs.activity.compose)
}