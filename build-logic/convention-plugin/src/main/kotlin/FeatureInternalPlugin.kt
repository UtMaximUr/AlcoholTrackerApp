import helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class FeatureInternalPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        pluginManager.run {
            apply<CodeQualityConventionPlugin>()
            apply<KoinAnnotationPlatformConventionPlugin>()
            apply(libs.plugins.jetbrainsCompose.get().pluginId)
            apply(libs.plugins.compose.compiler.get().pluginId)
        }

        extensions.configure<KotlinMultiplatformExtension> {

            sourceSets.commonMain {
                dependencies {
                    implementation(libs.arkivanov.decompose)
                    implementation(libs.arkivanov.decompose.extensions.compose)
                    implementation(libs.arkivanov.essenty.lifecycle.coroutines)
                    implementation(libs.kotlinx.coroutines.core)

                    val notNavigationFeatureModule = project.parent?.name?.contains("navigation") == false

                    if (notNavigationFeatureModule) {
                        implementation(libs.arkivanov.mvi)
                        implementation(libs.arkivanov.mvikotlin.extensions.coroutines)
                        implementation(project(":core:mvi-mappers:api"))
                    }

                    implementation(project(":core:decompose"))
                    implementation(project(":core:design"))
                }
            }
        }
    }
}