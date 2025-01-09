plugins {
    alias(libs.plugins.custom.android.application)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    id(libs.plugins.cocoapods.get().pluginId)
    id(libs.plugins.google.services.get().pluginId)
}

kotlin {
    cocoapods {
        summary = "compose app framework"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "composeApp"
            isStatic = true
            export(libs.arkivanov.decompose)
            export(libs.arkivanov.essenty.lifecycle)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(libs.koin.core)
            api(libs.arkivanov.decompose)
            api(libs.arkivanov.essenty.lifecycle)

            implementation(projects.di)
            implementation(projects.features.root.component)
        }

        androidMain.dependencies {
            implementation(libs.activity.compose)
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}

android {
    namespace = "com.utmaximur.alcoholtracker.android"
    defaultConfig {
        applicationId = "com.utmaximur.alcoholtracker"
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            excludes.add("/META-INF/ktor-http.kotlin_module")
            excludes.add("/META-INF/kotlinx-io.kotlin_module")
            excludes.add("/META-INF/atomicfu.kotlin_module")
            excludes.add("/META-INF/ktor-utils.kotlin_module")
            excludes.add("/META-INF/kotlinx-coroutines-io.kotlin_module")
            excludes.add("/META-INF/ktor-client-core.kotlin_module")
        }
    }
}