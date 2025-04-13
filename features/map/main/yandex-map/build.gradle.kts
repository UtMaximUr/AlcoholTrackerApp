import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
    alias(libs.plugins.buildkonfig)
    id(libs.plugins.cocoapods.get().pluginId)
}

kotlin {
    cocoapods {
        ios.deploymentTarget = "16.0"
        noPodspec()
        framework {
            baseName = "yandex_map_kit"
            isStatic = true
        }
        pod("YandexMapsMobile") {
            version = "4.13.0-lite"
        }
    }
}

dependencies {
    commonMainImplementation(projects.features.map.domain)
    androidMainImplementation(libs.yandex.map.mobile)
}

buildkonfig {
    packageName = "com.utmaximur.yandex_map"
    defaultConfigs {
        val keystoreFile = project.rootProject.file("apikeys.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val propertyKey = "MAP_API_KEY"
        val apiKey = properties.getProperty(propertyKey) ?: ""

        buildConfigField(
            FieldSpec.Type.STRING,
            propertyKey,
            apiKey,
        )
    }
}
