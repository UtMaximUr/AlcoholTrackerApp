plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainApi(libs.kotlinx.datetime)
    commonMainApi(libs.kotlinx.serialization.json)
}