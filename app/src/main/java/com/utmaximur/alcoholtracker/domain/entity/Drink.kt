package com.utmaximur.alcoholtracker.domain.entity


data class Drink(

    var id: String,
    var drink: String,
    var degree: List<String?>,
    var volume: List<String?>,
    var icon: String,
    var photo: String

)