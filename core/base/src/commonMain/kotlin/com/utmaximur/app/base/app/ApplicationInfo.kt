package com.utmaximur.app.base.app

data class ApplicationInfo(
    val packageName: String,
    val debugBuild: Boolean,
    val flavor: Flavor,
    val versionName: String,
    val versionCode: Int,
    val language: String,
)

/**
 *  [Qa]- Все функции, тестовый режим.
 *  [Standard] - Полный функционал.
 *  [NoMap]- Без карты (Yandex map).
 *  [NoAi] - Без AI (FusionBrain).
 *  [Minimal] - Без карты и AI.
 */
enum class Flavor(
    private val features: Set<Feature>
) {
    Qa(setOf(Feature.MAP, Feature.AI)),
    Standard(setOf(Feature.MAP, Feature.AI)),
    NoMap(setOf(Feature.AI)),
    NoAi(setOf(Feature.MAP)),
    Minimal(emptySet());

    fun isMapAvailable() = features.contains(Feature.MAP)
    fun isAiAvailable() = features.contains(Feature.AI)
}

private enum class Feature { MAP, AI }
