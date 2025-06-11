plugins {
    alias(libs.plugins.custom.android.library)
    alias(libs.plugins.custom.multiplatform.library)
    alias(libs.plugins.custom.feature.internal)
}

dependencies {
    commonMainImplementation(projects.features.statistic.money.main.domain)

    androidMainImplementation(libs.activity.compose)
    androidMainImplementation(libs.androidx.glance.appwidget)
    androidMainImplementation(libs.androidx.glance.material3)
    androidMainImplementation(libs.androidx.glance.preview)
}
