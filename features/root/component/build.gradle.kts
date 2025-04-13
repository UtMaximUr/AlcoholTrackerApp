plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
    alias(libs.plugins.custom.multiplatform.composeResources)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(libs.kotlinx.serialization.core)

    commonMainImplementation(projects.features.splashScreen.main.componentApi)
    commonMainImplementation(projects.features.calendar.navigation.componentApi)
    commonMainImplementation(projects.features.map.navigation.componentApi)
    commonMainImplementation(projects.features.statistic.main.componentApi)
    commonMainImplementation(projects.features.settings.navigation.componentApi)
    commonMainImplementation(projects.features.message.messageComponent)
    commonMainImplementation(projects.features.root.domain)
}