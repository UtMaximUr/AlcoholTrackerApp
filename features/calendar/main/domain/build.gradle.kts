plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.custom.koin.platform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    commonMainImplementation(compose.runtime)
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.datetime)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.arkivanov.mvi)
    commonMainImplementation(libs.arkivanov.mvikotlin.extensions.coroutines)
    commonMainImplementation(libs.arkivanov.essenty.lifecycle.coroutines)
    commonMainImplementation(libs.arkivanov.essenty.lifecycle)

    commonMainImplementation(projects.core.logging.api)
    commonMainImplementation(projects.core.utils)
    commonMainImplementation(projects.core.mviMappers.internal)
    commonMainImplementation(projects.features.calendar.domain)
    commonMainImplementation(projects.core.analytics.domain)
    commonMainImplementation(projects.core.analytics.params)
}

compose.resources {
    packageOfResClass = "calendar.domain.resources"
}