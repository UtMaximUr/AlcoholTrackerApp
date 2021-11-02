package com.utmaximur.alcoholtracker.util

import android.content.Context
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.dbo.TrackDBO
import com.utmaximur.alcoholtracker.util.extension.empty

/**
 *  converts old data into new data on migration
 */
fun TrackDBO.convertMigrationModel(context: Context): TrackDBO {

    var drink = this.drink
    var icon = this.icon
    var image = String.empty()
    val convertDegree = this.degree.replace(",", ".").toDouble().formatDegree1f()
    val convertVolume = this.volume.replace(
        context.getString(R.string.only_number_regex).toRegex(),
        ""
    ).trim()

    when (this.drink.getResString(context)) {
        context.getString(R.string.absent) -> {
            drink = "absent"
            icon = "ic_absent"
            image = "file:///android_asset/photo/absent.png"
        }
        context.getString(R.string.beer) -> {
            drink = "beer"
            icon = "ic_beer"
            image = "file:///android_asset/photo/beer.png"
        }
        context.getString(R.string.brandy) -> {
            drink = "brandy"
            icon = "ic_brandy"
            image = "file:///android_asset/photo/brandy.png"
        }
        context.getString(R.string.champagne) -> {
            drink = "champagne"
            icon = "ic_champagne"
            image = "file:///android_asset/photo/champagne.png"
        }
        context.getString(R.string.cider) -> {
            drink = "cider"
            icon = "ic_cider"
            image = "file:///android_asset/photo/cider.png"
        }
        context.getString(R.string.cocktail) -> {
            drink = "cocktail"
            icon = "ic_cocktail"
            image = "file:///android_asset/photo/coctail.png"
        }
        context.getString(R.string.cognac) -> {
            drink = "cognac"
            icon = "ic_cognac"
            image = "file:///android_asset/photo/cognac.png"
        }
        context.getString(R.string.liqueur) -> {
            drink = "liqueur"
            icon = "ic_liqueur"
            image = "file:///android_asset/photo/liquor.png"
        }
        context.getString(R.string.shots) -> {
            drink = "shots"
            icon = "ic_shot"
            image = "file:///android_asset/photo/shots.png"
        }
        context.getString(R.string.tequila) -> {
            drink = "tequila"
            icon = "ic_tequila"
            image = "file:///android_asset/photo/tequila.png"
        }
        context.getString(R.string.vodka) -> {
            drink = "vodka"
            icon = "ic_vodka"
            image = "file:///android_asset/photo/vodka.png"
        }
        context.getString(R.string.whiskey) -> {
            drink = "whiskey"
            icon = "ic_whiskey"
            image = "file:///android_asset/photo/whiskey.png"
        }
        context.getString(R.string.rum) -> {
            drink = "rum"
            icon = "ic_rum"
            image = "file:///android_asset/photo/rum.png"
        }
        context.getString(R.string.gin) -> {
            drink = "gin"
            icon = "ic_gin"
            image = "file:///android_asset/photo/gin.png"
        }
        context.getString(R.string.wine) -> {
            drink = "wine"
            icon = "ic_wine"
            image = "file:///android_asset/photo/wine.png"
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
        icon,
        image
    )
}

fun TrackDBO.addImageField(context: Context): TrackDBO {
    var image = String.empty()
    when (this.drink.getResString(context)) {
        context.getString(R.string.absent) -> {
            image = "file:///android_asset/photo/absent.png"
        }
        context.getString(R.string.beer) -> {
            image = "file:///android_asset/photo/beer.png"
        }
        context.getString(R.string.brandy) -> {
            image = "file:///android_asset/photo/brandy.png"
        }
        context.getString(R.string.champagne) -> {
            image = "file:///android_asset/photo/champagne.png"
        }
        context.getString(R.string.cider) -> {
            image = "file:///android_asset/photo/cider.png"
        }
        context.getString(R.string.cocktail) -> {
            image = "file:///android_asset/photo/coctail.png"
        }
        context.getString(R.string.cognac) -> {
            image = "file:///android_asset/photo/cognac.png"
        }
        context.getString(R.string.liqueur) -> {
            image = "file:///android_asset/photo/liquor.png"
        }
        context.getString(R.string.shots) -> {
            image = "file:///android_asset/photo/shots.png"
        }
        context.getString(R.string.tequila) -> {
            image = "file:///android_asset/photo/tequila.png"
        }
        context.getString(R.string.vodka) -> {
            image = "file:///android_asset/photo/vodka.png"
        }
        context.getString(R.string.whiskey) -> {
            image = "file:///android_asset/photo/whiskey.png"
        }
        context.getString(R.string.rum) -> {
            image = "file:///android_asset/photo/rum.png"
        }
        context.getString(R.string.gin) -> {
            image = "file:///android_asset/photo/gin.png"
        }
        context.getString(R.string.wine) -> {
            image = "file:///android_asset/photo/wine.png"
        }
    }
    return TrackDBO(
        this.id,
        this.drink,
        this.volume,
        this.quantity,
        this.degree,
        this.event,
        this.price,
        this.date,
        this.icon,
        image
    )
}