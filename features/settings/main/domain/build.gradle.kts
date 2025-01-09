import com.codingfeline.buildkonfig.compiler.FieldSpec


plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.custom.koin.platform)
    alias(libs.plugins.buildkonfig)
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.arkivanov.mvi)
    commonMainImplementation(libs.arkivanov.mvikotlin.extensions.coroutines)
    commonMainImplementation(libs.arkivanov.essenty.lifecycle.coroutines)
    commonMainImplementation(libs.arkivanov.essenty.lifecycle)

    commonMainImplementation(projects.core.logging.api)
    commonMainImplementation(projects.core.analytics.domain)
    commonMainImplementation(projects.core.analytics.params)
    commonMainImplementation(projects.features.settings.domain)
    commonMainImplementation(projects.domain)
}

buildkonfig {
    packageName = "com.utmaximur.settings"
    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING,
            "URL_PRIVACY",
            "https://alcohol-tracker.flycricket.io/privacy.html"
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "URL_TERMS",
            "https://alcohol-tracker.flycricket.io/terms.html"
        )
    }
}