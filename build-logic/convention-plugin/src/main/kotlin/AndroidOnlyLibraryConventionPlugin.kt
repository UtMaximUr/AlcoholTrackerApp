import com.android.build.gradle.LibraryExtension
import helpers.configureAndroidMultiplatform
import helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * see build.gradle.kts gradlePlugin block
 */
@Suppress("unused")
internal class AndroidOnlyLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {
        with(pluginManager) {
            apply(libs.plugins.android.library.get().pluginId)
        }

        extensions.configure<LibraryExtension> {
            configureAndroidMultiplatform(this)
            defaultConfig.targetSdk = libs.versions.android.targetSdk.get().toInt()
        }
    }
}