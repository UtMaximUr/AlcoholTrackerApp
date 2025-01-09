plugins {
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.android.codequality)
}

dependencies {
    commonMainImplementation(libs.arkivanov.decompose)

    commonMainImplementation(projects.core.decompose)
    commonMainImplementation(projects.features.statistic.drink.main.domain)
}