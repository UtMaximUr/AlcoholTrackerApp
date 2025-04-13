import helpers.libs
import helpers.resources
import helpers.transformPathToComponentFormat
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
class MultiplatformComposeResourcesCommonConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.run {
                apply(libs.plugins.jetbrainsCompose.get().pluginId)
                apply(libs.plugins.compose.compiler.get().pluginId)
            }

            val compose = extensions.getByName("compose") as ComposeExtension

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.commonMain {
                    dependencies {
                        implementation(compose.dependencies.runtime)
                        implementation(compose.dependencies.components.resources)
                    }
                }
            }
            compose.resources {
                packageOfResClass = path.transformPathToComponentFormat()
            }
        }
    }
}