import com.utmaximur.buildsrc.Base
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjvm-default=compatibility", "-Xopt-in=kotlin.RequiresOptIn")
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-alpha02"
    }
}

dependencies {
    implementation(Libs.Navigation.navigation_compose)
    implementation(Libs.Compose.runtime)
    implementation(Libs.Compose.material)
}