plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(compose.material3)
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.splashScreen.main.componentApi)
    commonMainImplementation(projects.features.calendar.navigation.componentApi)
    commonMainImplementation(projects.features.statistic.main.componentApi)
    commonMainImplementation(projects.features.settings.navigation.componentApi)
    commonMainImplementation(projects.features.message.messageComponent)
    commonMainImplementation(projects.features.root.domain)
}

compose.resources {
    packageOfResClass = "root.resources"
}