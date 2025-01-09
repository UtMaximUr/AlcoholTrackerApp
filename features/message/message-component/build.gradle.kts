plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.custom.koin.platform)
}

dependencies {
    commonMainApi(compose.material3)
    commonMainImplementation(libs.arkivanov.decompose)
    commonMainImplementation(libs.arkivanov.decompose.extensions.compose)
    commonMainImplementation(libs.arkivanov.mvi)
    commonMainImplementation(libs.arkivanov.mvikotlin.extensions.coroutines)
    commonMainImplementation(libs.kotlinx.coroutines.core)

    commonMainImplementation(projects.features.message.domain)
}