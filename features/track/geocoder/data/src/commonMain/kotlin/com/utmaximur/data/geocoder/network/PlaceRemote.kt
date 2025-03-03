package com.utmaximur.data.geocoder.network

import kotlinx.serialization.Serializable

@Serializable
data class FeatureCollection(
    val features: List<Feature>
)

@Serializable
data class Feature(
    val geometry: Point,
    val properties: Properties
)

@Serializable
data class Point(
    val coordinates: List<Double>
)

@Serializable
data class Properties(
    val name: String,
    val description: String
)

