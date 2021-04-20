package com.utmaximur.alcoholtracker.util

import android.content.Context

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