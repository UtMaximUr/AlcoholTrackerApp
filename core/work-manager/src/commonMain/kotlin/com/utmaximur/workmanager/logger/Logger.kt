package com.utmaximur.workmanager.logger

interface Logger {

    enum class Type {
        INFO,
        ERROR
    }

    fun log(type: Type = Type.INFO, message: String)

    companion object {

        const val TAG = "WorkManager"

    }

}

internal expect object DefaultLogger : Logger {

    override fun log(type: Logger.Type, message: String)

}
