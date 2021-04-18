package com.utmaximur.alcoholtracker.util


import android.app.Service
import android.content.Context
import android.content.res.Resources
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.WindowMetrics
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.text.isDigitsOnly
import com.google.android.material.snackbar.Snackbar
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


private val density by lazy { Resources.getSystem().displayMetrics.density }

fun Int.dpToPx(): Int {
    return (this * density).roundToInt()
}

fun View.alphaView(context: Context) {
    val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.alpha)
    this.startAnimation(animation)
}

fun Context.getDisplayWidth(): Int {
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = windowManager.currentWindowMetrics
        val insets: Insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

fun String.getIdRaw(context: Context): Int {
    return context.resources.getIdentifier(
        this,
        "raw",
        context.packageName
    )
}

fun String.getResString(context: Context): String {
    val resIdString =
        context.resources.getIdentifier(
            this,
            "string",
            context.packageName
        )

    return if (resIdString != 0) {
        context.getString(resIdString)
    } else {
        this
    }
}

fun List<String?>.setVolumeUnit(context: Context): List<String?> {
    val volumeListUnit: ArrayList<String?> = ArrayList()
    if (this.first()?.contains(".")!!) {
        this.forEach {
            volumeListUnit.add(String.format(context.getString(R.string.unit_l), it))
        }
    } else {
        this.forEach {
            volumeListUnit.add(String.format(context.getString(R.string.unit_ml), it))
        }
    }
    return volumeListUnit
}

fun Float.format1f(): String {
    return String.format("%.1f", this)
}

fun Double.formatDegree1f(): String {
    return String.format("%.1f", this).replace(",", ".")
}

fun Long.formatDate(context: Context): String {
    val sdf = SimpleDateFormat(
        context.resources.getString(R.string.date_format_pattern),
        Locale.getDefault()
    )
    return String.format("%s", sdf.format(Date(this)))
}

fun String.formatVolume(context: Context, quantity: Int): String {
    return if (this.contains(".")) {
        if (this.isDigitsOnly()) {
            String.format(context.getString(R.string.unit_l), this.toDouble() * quantity)
        } else {
            //after migration it contains numbers and letters,
            //this method is needed to get only numbers from a string
            val digit: String =
                this.replace(context.getString(R.string.only_number_regex).toRegex(), "").trim()
            String.format(context.getString(R.string.unit_l), digit.toDouble() * quantity)
        }
    } else {
        if (this.isDigitsOnly()) {
            String.format(context.getString(R.string.unit_ml), this.toInt() * quantity)
        } else {
            val digit: String =
                this.replace(context.getString(R.string.only_number_regex).toRegex(), "").trim()
            String.format(context.getString(R.string.unit_ml), digit.toInt() * quantity)
        }
    }
}

fun View.hideKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
    this.clearFocus()
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.snackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

/**
 *  converts old data into new data on migration
 */
fun AlcoholTrack.convertMigrationModel(context: Context): AlcoholTrack {

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
    return AlcoholTrack(
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