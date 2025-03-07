package helpers

import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

/**
 * Configure base options for Detekt plugin
 */

internal class AppDetektPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        apply<DetektPlugin>()

        configure<DetektExtension> {

            val configFiles = file("$rootDir/codequality/detekt/detekt.yml")
            println(configFiles.name)
            config.setFrom(configFiles)

            autoCorrect = true
            parallel = true

            buildUponDefaultConfig = true

            // By default detekt does not check test source set and gradle specific files,
            // so hey have to be added manually
            source.from(
                files(
                    "src",
                    "$rootDir/buildSrc",
                    "$rootDir/build.gradle.kts",
                    "$rootDir/settings.gradle.kts",
                    "src/main/kotlin",
                    "src/main/java"
                )
            )
        }
    }
}