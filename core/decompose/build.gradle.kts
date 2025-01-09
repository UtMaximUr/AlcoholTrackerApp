plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainApi(compose.runtime)
    commonMainApi(compose.material3)
    commonMainImplementation(libs.arkivanov.decompose)
    commonMainImplementation(libs.arkivanov.decompose.extensions.compose)
    commonMainImplementation(libs.kotlinx.serialization.core)
}