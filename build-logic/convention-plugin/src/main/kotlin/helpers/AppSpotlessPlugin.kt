package helpers

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

internal class AppSpotlessPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        apply<SpotlessPlugin>()

        configure<SpotlessExtension> {
            kotlin {
                ktlint()
                // Doesn't work without target
                target("**/*.kt")
                trimTrailingWhitespace()
                indentWithSpaces()
            }

            java {
                // Doesn't work without target
                // https://github.com/diffplug/spotless/issues/111
                target("**/*.java")
                importOrder()
                removeUnusedImports()
                googleJavaFormat()
            }
            format("misc") {
                target("**/*.gradle", "**/*md", "**/*gitignore")
                indentWithSpaces()
                trimTrailingWhitespace()
            }
            kotlinGradle {
                target("*.gradle.kts")
                ktlint()
                trimTrailingWhitespace()
                indentWithSpaces()
            }
            format("xml") {
                target("**/*.xml")
                indentWithSpaces()
                trimTrailingWhitespace()
            }
            freshmark {
                target("**/*.md")
            }
        }
    }
}