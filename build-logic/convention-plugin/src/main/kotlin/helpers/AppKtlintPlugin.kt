package helpers

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

internal class AppKtlintPlugin : Plugin<Project> {

    override fun apply(target: Project) = target.run {
        apply<KtlintPlugin>()

        configure<KtlintExtension> {
            debug.set(true)
            verbose.set(true)
            android.set(true)
            enableExperimentalRules.set(true)
            outputToConsole.set(true)
            outputColorName.set("RED")
            //need to set version for work fine https://github.com/JLLeitschuh/ktlint-gradle/issues/652
            version.set("0.50.0")


            reporters {
                reporter(ReporterType.PLAIN)
                reporter(ReporterType.CHECKSTYLE)
                reporter(ReporterType.HTML)
            }

            filter {
                exclude {
                    projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated")
                }
                include("**/kotlin/**")
            }
        }
    }
}