import com.android.build.gradle.internal.utils.KSP_PLUGIN_ID
import com.google.devtools.ksp.gradle.KspExtension
import helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

@Suppress("unused")
internal class KoinAnnotationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        pluginManager.apply(KSP_PLUGIN_ID)

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain {
                kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
                dependencies {
                    implementation(libs.koin.core)
                    implementation(libs.koin.annotations)
                }
            }
        }

        tasks.withType<KotlinCompilationTask<*>>().configureEach {
            if (name != "kspCommonMainKotlinMetadata") {
                dependsOn("kspCommonMainKotlinMetadata")
            }
        }
        dependencies {
            with(libs.koin.kspCompiler.get()) {
                add("kspCommonMainMetadata", this)
            }
        }
        extensions.configure<KspExtension> {
            arg("KOIN_DEFAULT_MODULE", "false")
        }
    }
}