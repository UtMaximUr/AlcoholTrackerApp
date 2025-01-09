import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.ktorfit.lib)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.logging)

            implementation(projects.core.base)
            implementation(projects.coreData.remote.client.api)
            implementation(projects.coreData.remote.errors)
        }

        androidMain.dependencies {
            implementation(libs.ktor.android)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

buildkonfig {
    packageName = "com.utmaximur.client"
    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING,
            "BASE_URL",
            "https://raw.githubusercontent.com/UtMaximUr/AlcoholTrackerApp/refs/heads/main/app/src/main/assets/"
        )
    }
}