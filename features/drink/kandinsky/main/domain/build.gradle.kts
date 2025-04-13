plugins {
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
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.arkivanov.mvi)
    commonMainImplementation(libs.arkivanov.mvikotlin.extensions.coroutines)
    commonMainImplementation(libs.arkivanov.essenty.lifecycle.coroutines)
    commonMainImplementation(libs.arkivanov.essenty.lifecycle)

    commonMainImplementation(projects.core.base)
    commonMainImplementation(projects.core.utils)
    commonMainImplementation(projects.core.mviMappers.internal)
    commonMainImplementation(projects.core.internetConnection)
    commonMainImplementation(projects.core.analytics.domain)
    commonMainImplementation(projects.core.analytics.params)
    commonMainImplementation(projects.domain.api)
    commonMainImplementation(projects.features.drink.kandinsky.domain)
    commonMainImplementation(projects.features.drink.drinkImageModal.domain)
    commonMainImplementation(projects.features.message.domain)
}

compose.resources {
    packageOfResClass = "kandinsky.domain.resources"
}
