plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.custom.koin.platform)
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.kotlinx.datetime)
    commonMainImplementation(libs.arkivanov.mvi)
    commonMainImplementation(libs.arkivanov.mvikotlin.extensions.coroutines)
    commonMainImplementation(libs.arkivanov.essenty.lifecycle.coroutines)
    commonMainImplementation(libs.arkivanov.essenty.lifecycle)

    commonMainImplementation(projects.core.base)
    commonMainImplementation(projects.core.logging.api)
    commonMainImplementation(projects.core.utils)
    commonMainImplementation(projects.core.mviMappers.internal)
    commonMainImplementation(projects.core.analytics.domain)
    commonMainImplementation(projects.core.analytics.params)
    commonMainImplementation(projects.features.track.detailTrack.domain)
    commonMainImplementation(projects.features.track.calculator.domain)
    commonMainImplementation(projects.features.track.datePicker.domain)
    commonMainImplementation(projects.features.confirmDialog.domain)
}