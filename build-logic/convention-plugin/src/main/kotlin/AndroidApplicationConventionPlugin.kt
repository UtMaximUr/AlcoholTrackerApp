import com.android.build.api.dsl.ApplicationExtension
import helpers.configureKotlinAndroid
import helpers.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * see build.gradle.kts gradlePlugin block
 */
@Suppress("unused")
internal class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = target.run {

        with(pluginManager) {
            apply(libs.plugins.android.application.get().pluginId)
            //do not need, cause use inside composeApp
            //apply(KOTLIN_ANDROID_PLUGIN_ID)
        }

        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = libs.versions.android.targetSdk.get().toInt()
        }
    }
}