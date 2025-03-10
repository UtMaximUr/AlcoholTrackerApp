plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
    id(libs.plugins.cocoapods.get().pluginId)
}

kotlin {
    cocoapods {
        ios.deploymentTarget = "15.0"
        noPodspec()
        framework {
            baseName = "internet_connection"
            isStatic = true
        }
        pod("Reachability", "~> 3.2")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(projects.core.logging.api)
        }
    }
}