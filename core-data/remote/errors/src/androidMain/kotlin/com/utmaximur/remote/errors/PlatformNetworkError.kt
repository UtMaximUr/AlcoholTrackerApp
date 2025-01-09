package com.utmaximur.remote.errors

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

actual fun Throwable.isNetworkConnectionError(): Boolean {
    return when (this) {
        is ConnectException,
        is SocketTimeoutException,
        is TimeoutException,
        is UnknownHostException -> true
        else -> false
    }
}