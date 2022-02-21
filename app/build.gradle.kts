import com.utmaximur.buildsrc.Base
import com.utmaximur.buildsrc.BuildPlugins
import com.utmaximur.buildsrc.Libs

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
}


android {
    compileSdk = Base.compileSdk

    defaultConfig {
        applicationId = Base.applicationId
        minSdk = Base.minSdk
        targetSdk = Base.targetSdk
        versionCode = Base.versionCode
        versionName = Base.versionName

        testInstrumentationRunner = Base.androidTestInstrumentation

        vectorDrawables {
            useSupportLibrary = true
        }
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
        freeCompilerArgs = listOf("-Xjvm-default=compatibility", "-Xopt-in=kotlin.RequiresOptIn")
        jvmTarget = Base.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildPlugins.compose_version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":utils"))
    implementation(project(":navigation"))
    implementation(project(":feature_update"))
    implementation(project(":feature_calendar"))
    implementation(project(":feature_statistic"))
    implementation(project(":feature_settings"))
    implementation(project(":feature_calculator"))
    implementation(project(":feature_create_drink"))
    implementation(project(":feature_create_track"))

    implementation(Libs.Core.appcompat)
    implementation(Libs.Core.material)

    implementation(Libs.Compose.material)
    implementation(Libs.Compose.activity)
    implementation(Libs.Compose.system_ui_controller)

    implementation(Libs.Navigation.navigation_compose)

    implementation(Libs.Core.multidex)

    implementation(platform(Libs.FireBase.firebase))
    implementation(Libs.FireBase.firebase_analytics)
    implementation(Libs.FireBase.firebase_crashlytics)

    implementation(Libs.Hilt.hilt_android)
    kapt(Libs.Hilt.hilt_compiler)

    implementation(Libs.Timber.timber)
}