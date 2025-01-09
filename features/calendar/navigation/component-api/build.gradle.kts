plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
}

dependencies {
    commonMainImplementation(projects.core.decompose)
}