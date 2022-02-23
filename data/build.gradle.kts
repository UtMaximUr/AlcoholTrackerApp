import com.utmaximur.buildsrc.Base
import com.utmaximur.buildsrc.Libs

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Base.compileSdk

    defaultConfig {
        minSdk = Base.minSdk
        targetSdk = Base.targetSdk

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
                arguments["room.incremental"] = "true"
                arguments["room.expandProjection"] = "true"
            }
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(Libs.Core.coreKtx)

    implementation(Libs.Room.room)
    implementation(Libs.Room.room_ktx)
    kapt(Libs.Room.room_compiler)

    implementation(Libs.Json.gson)

    implementation(Libs.Hilt.hilt_android)
    kapt(Libs.Hilt.hilt_compiler)

    implementation(Libs.InApUpdates.in_ap_updates)
}