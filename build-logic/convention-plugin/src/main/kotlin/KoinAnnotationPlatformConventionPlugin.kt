import com.android.build.gradle.internal.utils.KSP_PLUGIN_ID
import helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class KoinAnnotationPlatformConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        pluginManager.apply(KSP_PLUGIN_ID)

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain {
                dependencies {
                    implementation(libs.koin.core)
                    implementation(libs.koin.annotations)
                }
            }
        }

        dependencies {
            with(libs.koin.kspCompiler) {
                add("kspAndroid", this)
                add("kspIosX64", this)
                add("kspIosArm64", this)
                add("kspIosSimulatorArm64", this)
                add("kspIosSimulatorArm64Test", this)
            }
        }
    }
}