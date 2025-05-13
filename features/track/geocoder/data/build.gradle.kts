import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.custom.koin.platform)
    alias(libs.plugins.custom.ktorfit.plugin)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildkonfig)
}

dependencies {
    commonMainImplementation(libs.store5)
    commonMainImplementation(libs.ktorfit.lib)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.core.base)
    commonMainImplementation(projects.coreData.models)
    commonMainImplementation(projects.coreData.databaseRoom)
    commonMainImplementation(projects.core.utils)
    commonMainImplementation(projects.features.track.geocoder.domain)
}

buildkonfig {
    packageName = "com.utmaximur.geocoder"
    defaultConfigs {
        val keystoreFile = project.rootProject.file("apikeys.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val propertyKey = "GEOCODER_KEY"
        val apiKey = properties.getProperty(propertyKey) ?: ""

        buildConfigField(
            FieldSpec.Type.STRING,
            propertyKey,
            apiKey,
        )
    }
}
