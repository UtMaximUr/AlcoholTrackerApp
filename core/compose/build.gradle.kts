plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    commonMainApi(compose.ui)
    commonMainApi(compose.foundation)
    commonMainApi(compose.material3)
}