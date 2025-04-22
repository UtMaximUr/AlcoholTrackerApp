plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    commonMainImplementation(compose.runtime)
    commonMainImplementation(libs.arkivanov.decompose)
}