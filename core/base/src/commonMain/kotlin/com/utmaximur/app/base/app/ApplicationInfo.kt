package com.utmaximur.app.base.app

data class ApplicationInfo(
    val packageName: String,
    val debugBuild: Boolean,
    val flavor: Flavor,
    val versionName: String,
    val versionCode: Int,
    val language: String,
)

enum class Flavor {
    Qa,
    Standard,
    WithoutMap,
}
