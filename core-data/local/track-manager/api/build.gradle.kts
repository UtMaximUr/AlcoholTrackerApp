plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
}

dependencies {
    commonMainImplementation(libs.kotlinx.coroutines.core)

    commonMainApi(projects.coreData.models)
    commonMainApi(projects.coreData.databaseRoom)
    commonMainImplementation(projects.domain)
}