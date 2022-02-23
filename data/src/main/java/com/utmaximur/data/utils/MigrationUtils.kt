package com.utmaximur.data.utils

import com.utmaximur.data.local_data_source.dbo.TrackDBO

/**
 *  converts old data into new data on migration
 */
fun TrackDBO.convertMigrationModel(): TrackDBO {

    var drink = this.drink
    var icon = this.icon
    var image = String.empty()
    val convertDegree = this.degree.replace(",", ".").toDouble().formatDegree1f()
    val convertVolume = this.volume.replace(("[^0-9, .]").toRegex(),
        ""
    ).trim()

    when (this.drink) {
        "absent" -> {
            drink = "absent"
            icon = "ic_absent"
            image = "file:///android_asset/photo/absent.png"
        }
        "beer" -> {
            drink = "beer"
            icon = "ic_beer"
            image = "file:///android_asset/photo/beer.png"
        }
        "brandy" -> {
            drink = "brandy"
            icon = "ic_brandy"
            image = "file:///android_asset/photo/brandy.png"
        }
        "champagne" -> {
            drink = "champagne"
            icon = "ic_champagne"
            image = "file:///android_asset/photo/champagne.png"
        }
        "cider" -> {
            drink = "cider"
            icon = "ic_cider"
            image = "file:///android_asset/photo/cider.png"
        }
        "cocktail" -> {
            drink = "cocktail"
            icon = "ic_cocktail"
            image = "file:///android_asset/photo/coctail.png"
        }
        "cognac" -> {
            drink = "cognac"
            icon = "ic_cognac"
            image = "file:///android_asset/photo/cognac.png"
        }
        "liqueur" -> {
            drink = "liqueur"
            icon = "ic_liqueur"
            image = "file:///android_asset/photo/liquor.png"
        }
        "shots" -> {
            drink = "shots"
            icon = "ic_shot"
            image = "file:///android_asset/photo/shots.png"
        }
        "tequila" -> {
            drink = "tequila"
            icon = "ic_tequila"
            image = "file:///android_asset/photo/tequila.png"
        }
        "vodka" -> {
            drink = "vodka"
            icon = "ic_vodka"
            image = "file:///android_asset/photo/vodka.png"
        }
        "whiskey" -> {
            drink = "whiskey"
            icon = "ic_whiskey"
            image = "file:///android_asset/photo/whiskey.png"
        }
        "rum" -> {
            drink = "rum"
            icon = "ic_rum"
            image = "file:///android_asset/photo/rum.png"
        }
        "gin" -> {
            drink = "gin"
            icon = "ic_gin"
            image = "file:///android_asset/photo/gin.png"
        }
        "wine" -> {
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

fun TrackDBO.addImageField(): TrackDBO {
    var image = String.empty()
    when (this.drink) {
        "absent" -> {
            image = "file:///android_asset/photo/absent.png"
        }
        "beer" -> {
            image = "file:///android_asset/photo/beer.png"
        }
       "brandy" -> {
            image = "file:///android_asset/photo/brandy.png"
        }
        "champagne" -> {
            image = "file:///android_asset/photo/champagne.png"
        }
        "cider" -> {
            image = "file:///android_asset/photo/cider.png"
        }
        "cocktail" -> {
            image = "file:///android_asset/photo/coctail.png"
        }
        "cognac" -> {
            image = "file:///android_asset/photo/cognac.png"
        }
        "liqueur" -> {
            image = "file:///android_asset/photo/liquor.png"
        }
        "shots" -> {
            image = "file:///android_asset/photo/shots.png"
        }
        "tequila" -> {
            image = "file:///android_asset/photo/tequila.png"
        }
        "vodka" -> {
            image = "file:///android_asset/photo/vodka.png"
        }
        "whiskey" -> {
            image = "file:///android_asset/photo/whiskey.png"
        }
        "rum" -> {
            image = "file:///android_asset/photo/rum.png"
        }
        "gin" -> {
            image = "file:///android_asset/photo/gin.png"
        }
        "wine" -> {
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

private fun Double.formatDegree1f(): String {
    return String.format("%.1f", this).replace(",", ".")
}