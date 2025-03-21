
import com.android.build.gradle.internal.utils.KSP_PLUGIN_ID
import helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class KtorfitConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        pluginManager.apply(KSP_PLUGIN_ID)
        pluginManager.apply(libs.plugins.ktorfit.get().pluginId)

        extensions.configure<KotlinMultiplatformExtension> {
            sourceSets.commonMain {
                dependencies {
                    api(libs.ktorfit.lib)
                }
            }
        }

        dependencies {
            with(libs.ktorfit.ksp) {
                add("kspCommonMainMetadata", this)
                add("kspAndroid", this)
                add("kspIosX64", this)
                add("kspIosArm64", this)
                add("kspIosSimulatorArm64", this)
                add("kspIosSimulatorArm64Test", this)
            }
        }
    }
}

