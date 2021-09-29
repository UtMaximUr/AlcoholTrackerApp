package com.utmaximur.alcoholtracker.util

import android.content.Context
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.dbo.TrackDBO

/**
 *  converts old data into new data on migration
 */
fun TrackDBO.convertMigrationModel(context: Context): TrackDBO {

    var drink = this.drink
    var icon = this.icon
    val convertDegree = this.degree.replace(",", ".").toDouble().formatDegree1f()
    val convertVolume = this.volume.replace(
        context.getString(R.string.only_number_regex).toRegex(),
        ""
    ).trim()

    when (this.drink) {
        context.getString(R.string.absent) -> {
            drink = "absent"
            icon = "ic_absent"
        }
        context.getString(R.string.beer) -> {
            drink = "beer"
            icon = "ic_beer"
        }
        context.getString(R.string.brandy) -> {
            drink = "brandy"
            icon = "ic_brandy"
        }
        context.getString(R.string.champagne) -> {
            drink = "champagne"
            icon = "ic_champagne"
        }
        context.getString(R.string.cider) -> {
            drink = "cider"
            icon = "ic_cider"
        }
        context.getString(R.string.cocktail) -> {
            drink = "cocktail"
            icon = "ic_cocktail"
        }
        context.getString(R.string.cognac) -> {
            drink = "cognac"
            icon = "ic_cognac"
        }
        context.getString(R.string.liqueur) -> {
            drink = "liqueur"
            icon = "ic_liqueur"
        }
        context.getString(R.string.shots) -> {
            drink = "shots"
            icon = "ic_shot"
        }
        context.getString(R.string.tequila) -> {
            drink = "tequila"
            icon = "ic_tequila"
        }
        context.getString(R.string.vodka) -> {
            drink = "vodka"
            icon = "ic_vodka"
        }
        context.getString(R.string.whiskey) -> {
            drink = "whiskey"
            icon = "ic_whiskey"
        }
        context.getString(R.string.rum) -> {
            drink = "rum"
            icon = "ic_rum"
        }
        context.getString(R.string.gin) -> {
            drink = "gin"
            icon = "ic_gin"
        }
        context.getString(R.string.wine) -> {
            drink = "wine"
            icon = "ic_wine"
        }
    }
    return TrackDBO(
        this.id,
        drink,
        convertVolume,
        this.quantity,
        convertDegree,
        this.event,
        this.price,
        this.date,
        icon
    )
}