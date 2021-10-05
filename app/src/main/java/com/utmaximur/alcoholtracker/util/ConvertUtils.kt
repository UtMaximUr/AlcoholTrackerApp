package com.utmaximur.alcoholtracker.util


import android.content.Context
import android.content.res.Resources
import androidx.core.text.isDigitsOnly
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.domain.entity.Track
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


private val density by lazy { Resources.getSystem().displayMetrics.density }

fun Int.dpToPx(): Int {
    return (this * density).roundToInt()
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

fun Track.getSafeDoseOfAlcohol(context: Context): String {
    return if (volume.contains(".")) {
        // convert ml to l
        val volume = volume.toDouble() * 1000
        String.format(
            context.getString(R.string.safe_dose),
            ((quantity * volume) / 100 * degree.toDouble()).formatDegree1f()
        )
    } else {
        String.format(
            context.getString(R.string.safe_dose),
            ((quantity * volume.toDouble()) / 100 * degree.toDouble()).formatDegree1f()
        )
    }
}
