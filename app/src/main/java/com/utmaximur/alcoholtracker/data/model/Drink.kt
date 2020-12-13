package com.utmaximur.alcoholtracker.data.model

data class Drink(
    var drink: String,
    var alcoholStrength: List<String?>,
    var alcoholVolume: Int,
    var icon: Int,
    var image: Int

)