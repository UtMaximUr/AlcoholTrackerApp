plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.coreData.remote.errors)
            implementation(projects.core.logging.api)
            implementation(projects.coreData.remote.errors)
            implementation(projects.features.message.domain)
            api(projects.core.mviMappers.api)
        }
    }
}