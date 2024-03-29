package com.utmaximur.utils


import android.content.Context
import androidx.core.text.isDigitsOnly
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


fun List<String?>.setVolumeUnit(context: Context): List<String> {
    val volumeListUnit: ArrayList<String> = ArrayList()
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

fun String.digitOnly(): String {
    return this.replace("[^0-9, .]".toRegex(), "").trim()
}

fun Float.format1f(): String {
    return String.format("%.1f", this)
}

fun String.formatStringTo1f(): String {
    return if (this.isNotEmpty()) {
        String.format("%.1f", this.toFloat()).replace(",", ".")
    } else {
        String.empty()
    }
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

fun Long.formatDatePicker(context: Context): String {
    val sdf = SimpleDateFormat(
        context.resources.getString(R.string.date_picker_format_pattern),
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

            val volume = String.format("%.3f", digit.toDouble() * quantity)
            String.format(context.getString(R.string.unit_l), volume)
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

fun Context.getSafeDoseOfAlcohol(volume: String, quantity: Int, degree: String): String {
    val digitVolume: String =
        volume.replace(this.getString(R.string.only_number_regex).toRegex(), "").trim()
    return if (volume.contains(".")) {
        // convert ml to l
        val v = digitVolume.toDouble() * 1000
        String.format(
            this.getString(R.string.safe_dose),
            ((quantity * v) / 100 * degree.toDouble()).formatDegree1f()
        )
    } else {
        String.format(
            this.getString(R.string.safe_dose),
            ((quantity * digitVolume.toDouble()) / 100 * degree.toDouble()).formatDegree1f()
        )
    }
}
