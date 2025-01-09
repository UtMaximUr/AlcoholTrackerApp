plugins {
    `kotlin-dsl`
}

group = "com.utmaximur.buildlogic"

// This repositories are required to connect non-official
repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
}

dependencies {

    compileOnly(libs.plugin.android)
    compileOnly(libs.gradlePlugins.kotlin.core)
    implementation(libs.ksp.gradlePlugin)
    compileOnly(libs.gradlePlugins.jb.compose)
    /*
    * compileOnly makes it impossible to load class
    * for my custom plugin if i loaded the settings.gradle.kts plugin
    * You also need to remove the loading of plugins in the project block
     */
    implementation(libs.detekt.plugin)
    implementation(libs.ktlint.jlleitschuh.plugin)
    implementation(libs.spotless.plugin)

    // TODO подождать пока эта фича появится в гредле
    // Мы хотим получать доступ к libs из наших convention плагинов, но гредл на текущий момент не умеет прокидывать
    // version catalogs. Поэтому используем костыль отсюда - https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "custom.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "custom.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("multiplatformLibrary") {
            id = "custom.multiplatformLibrary"
            implementationClass = "MultiplatformConventionPlugin"
        }
        register("androidMultiplatformLibrary") {
            id = "custom.androidMultiplatformLibrary"
            implementationClass = "AndroidOnlyLibraryConventionPlugin"
        }
        register("androidAppQualityPlugin") {
            id = "custom.android.codequality"
            implementationClass = "CodeQualityConventionPlugin"
        }
        register("koinPlugin") {
            id = "custom.koinPlugin"
            implementationClass = "KoinAnnotationConventionPlugin"
        }
        register("koinPlatformPlugin") {
            id = "custom.koinPlatformPlugin"
            implementationClass = "KoinAnnotationPlatformConventionPlugin"
        }
        register("featureInternalPlugin") {
            id = "custom.featureInternal"
            implementationClass = "FeatureInternalPlugin"
        }
        register("ktorfitPlugin") {
            id = "custom.ktorfitPlugin"
            implementationClass = "KtorfitConventionPlugin"
        }
    }
}