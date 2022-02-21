package com.utmaximur.domain.entity


data class Drink(

    val id: String,
    val drink: String,
    val degree: List<String>,
    val volume: List<String>,
    val icon: String,
    val photo: String

)