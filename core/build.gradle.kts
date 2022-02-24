import com.utmaximur.buildsrc.Base
import com.utmaximur.buildsrc.BuildPlugins
import com.utmaximur.buildsrc.Libs

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = Base.compileSdk

    defaultConfig {
        minSdk = Base.minSdk
        targetSdk = Base.targetSdk
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(Base.proguardOptimize), Base.proguardRulesPro
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        freeCompilerArgs = listOf(Base.xJvm, Base.xOpt)
        jvmTarget = Base.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildPlugins.compose_version
    }
}

dependencies {
    api(project(":domain"))
    api(project(":utils"))
    api(project(":navigation"))

    api(Libs.Core.coreKtx)
    api(Libs.Compose.material)

    api(Libs.Compose.glide)
    api(Libs.Hilt.hilt_compose)
    api(Libs.Compose.livedata)
    api(Libs.Navigation.navigation_compose)
    api(Libs.Compose.pager)

}