import java.util.Properties
import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    alias(libs.plugins.custom.android.library)
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
    commonMainImplementation(projects.core.utils)
    commonMainImplementation(projects.core.logging.api)
    commonMainImplementation(projects.core.workManager)
    commonMainImplementation(projects.coreData.models)
    commonMainImplementation(projects.coreData.databaseRoom)
    commonMainImplementation(projects.features.drink.kandinsky.domain)
}

buildkonfig {
    packageName = "com.utmaximur.kandisky"
    defaultConfigs {
        val keystoreFile = project.rootProject.file("apikeys.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val propertyKandinskyKey = "KANDINSKY_API_KEY"
        val propertyKandinskySecret = "KANDINSKY_SECRET_KEY"
        val apiKey = properties.getProperty(propertyKandinskyKey) ?: ""
        val secret = properties.getProperty(propertyKandinskySecret) ?: ""

        buildConfigField(
            FieldSpec.Type.STRING,
            propertyKandinskyKey,
            apiKey,
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            propertyKandinskySecret,
            secret,
        )
    }
}
