package helpers

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure base Kotlin with Android options
 */

internal fun Project.configureAndroidMultiplatform(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {

    commonExtension.apply {
        compileSdk = libs.versions.android.compileSdk.get().toInt()

        defaultConfig {
            minSdk = libs.versions.android.minSdk.get().toInt()
        }

        compileOptions {
            // Flag to enable support for the new language APIs
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

        sourceSets {
            getByName("main") {
                java.srcDirs("src/kotlin")
                kotlin.srcDirs("src/kotlin")
                manifest.srcFile("src/androidMain/AndroidManifest.xml")
            }
        }

        namespace = path.replace("-", "").split(":")
            .joinToString(".").removePrefix(".")

        dependencies {
            val bom = libs.desugar.jdk.libs.get()
            add("coreLibraryDesugaring", platform(bom))
        }
    }
}
