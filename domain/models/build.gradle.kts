plugins {
    alias(libs.plugins.custom.multiplatform.library)
}

dependencies {
    commonMainImplementation(projects.domain.api)
    commonMainImplementation(libs.kotlinx.datetime)
}
