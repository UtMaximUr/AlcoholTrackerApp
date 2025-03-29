package com.utmaximur.workmanager.logger

import android.util.Log

internal actual object DefaultLogger : Logger {

    actual override fun log(type: Logger.Type, message: String) {
        when (type) {
            Logger.Type.INFO -> Log.i(Logger.TAG, message)
            Logger.Type.ERROR -> Log.e(Logger.TAG, message)
        }
    }

}
