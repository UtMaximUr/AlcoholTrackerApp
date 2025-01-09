plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    commonMainImplementation(compose.material3)
    commonMainImplementation(compose.components.resources)

    androidMainImplementation(libs.activity.compose)
}

compose.resources {
    packageOfResClass = "permission.resources"
}