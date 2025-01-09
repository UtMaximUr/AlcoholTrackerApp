plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.base)
            implementation(projects.coreData.databaseRoom)
            implementation(projects.coreData.models)
            implementation(projects.coreData.local.drinkManager.api)
            implementation(projects.domain)
        }
    }
}