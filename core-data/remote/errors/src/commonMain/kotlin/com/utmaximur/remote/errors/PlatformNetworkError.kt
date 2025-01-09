package com.utmaximur.remote.errors

expect fun Throwable.isNetworkConnectionError(): Boolean

data object NoNetwork : NetworkError()