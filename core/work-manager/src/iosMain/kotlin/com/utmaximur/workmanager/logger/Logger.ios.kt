package com.utmaximur.workmanager.logger

import platform.Foundation.NSLog

internal actual object DefaultLogger : Logger {

    actual override fun log(type: Logger.Type, message: String) {
        val formattedMessage = when (type) {
            Logger.Type.INFO -> "INFO: $message"
            Logger.Type.ERROR -> "❌ ERROR: $message"
        }
        NSLog("%s - %s", Logger.TAG, formattedMessage)
    }

}
