import helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

@Suppress("unused")
internal class FeatureApiPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        apply(libs.plugins.custom.android.codequality)

        extensions.configure<KotlinMultiplatformExtension> {

            sourceSets.commonMain {
                dependencies {
                    implementation(project( ":core:decompose"))
                }
            }
        }
    }
}