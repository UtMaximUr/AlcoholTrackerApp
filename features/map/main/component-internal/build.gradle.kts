import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildkonfig)
}

dependencies {
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.datetime)
    commonMainImplementation(libs.kotlinx.serialization.core)
    commonMainImplementation(libs.bundles.coil)

    commonMainImplementation(projects.features.map.main.componentApi)
    commonMainImplementation(projects.features.map.main.domain)
    commonMainImplementation(projects.features.map.domain)

    androidMainImplementation(libs.yandex.map.mobile)
}

compose.resources {
    packageOfResClass = "map.resources"
}

buildkonfig {
    packageName = "com.utmaximur.map"
    defaultConfigs {
        val keystoreFile = project.rootProject.file("apikeys.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val propertyKey = "MAP_API_KEY"
        val apiKey = properties.getProperty(propertyKey) ?: ""

        buildConfigField(
            FieldSpec.Type.STRING,
            propertyKey,
            apiKey
        )
    }
}