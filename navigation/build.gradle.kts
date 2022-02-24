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
    implementation(Libs.Navigation.navigation_compose)
    implementation(Libs.Compose.runtime)
    implementation(Libs.Compose.material)
}