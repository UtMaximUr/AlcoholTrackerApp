package helpers

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

/**
 * Configure base Kotlin with Android options
 */

@OptIn(ExperimentalKotlinGradlePluginApi::class)
internal fun Project.configureKotlinMultiplatform(
    kotlinMultiplatformExtension: KotlinMultiplatformExtension
) {
    kotlinMultiplatformExtension.apply {
        applyDefaultHierarchyTemplate()
        val libs = the<LibrariesForLibs>()
        if (pluginManager.hasPlugin(libs.plugins.android.library.get().pluginId)
            || pluginManager.hasPlugin(libs.plugins.android.application.get().pluginId)
        ) {
            androidTarget()
        } else {
            jvm()
        }

        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64(),
        )

        targets.withType<KotlinNativeTarget>().configureEach {
            binaries.all {
                // Add linker flag for SQLite. See:
                // https://github.com/touchlab/SQLiter/issues/77
                //linkerOpts("-lsqlite3")
            }
            compilerOptions {
                // Try out preview custom allocator in K/N 1.9
                // https://kotlinlang.org/docs/whatsnew19.html#preview-of-custom-memory-allocator
                freeCompilerArgs.add("-Xallocator=custom")

                //https://kotlinlang.org/docs/whatsnew19.html#compiler-option-for-c-interop-implicit-integer-conversions
                freeCompilerArgs.add("-XXLanguage:+ImplicitSignedToUnsignedIntegerConversion")

                // Enable debug symbols:
                // https://kotlinlang.org/docs/native-ios-symbolication.html
                freeCompilerArgs.add("-Xadd-light-debug=enable")

                // Various opt-ins
                freeCompilerArgs.addAll(
                    "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
                    "-opt-in=kotlinx.cinterop.BetaInteropApi",
                )
            }
        }
        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }

        //https://stackoverflow.com/questions/36465824/android-studio-task-testclasses-not-found-in-project/78017056#78017056
        task("testClasses")
    }
}
