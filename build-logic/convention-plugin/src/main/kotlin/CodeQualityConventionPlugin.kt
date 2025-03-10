import helpers.AppDetektPlugin
import helpers.AppKtlintPlugin
import helpers.AppSpotlessPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

/**
 * see build.gradle.kts gradlePlugin block
 */
@Suppress("unused")
internal class CodeQualityConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        apply<AppDetektPlugin>()
        apply<AppKtlintPlugin>()
        apply<AppSpotlessPlugin>()

//        tasks.named("preBuild") {
//            /**
//             * just to check in which module the tasks are executed
//             */
//            val name = "${project.parent?.name}:${project.name}"
//            println("inside tasks -> $name")
//
//            dependsOn("ktlintFormat")
//            dependsOn("spotlessCheck")
//            dependsOn("spotlessApply")
//            dependsOn("detekt")
//        }
        Unit
    }
}