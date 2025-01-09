plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    commonMainImplementation(compose.material)
    commonMainImplementation(compose.material3)
    commonMainImplementation(compose.components.resources)
    commonMainImplementation(libs.shimmer.compose)
    commonMainImplementation(libs.bundles.coil)

    commonMainImplementation(projects.core.mviMappers.api)
    commonMainImplementation(projects.core.mviMappers.internal)
}

compose.resources {
    packageOfResClass = "design.resources"
}