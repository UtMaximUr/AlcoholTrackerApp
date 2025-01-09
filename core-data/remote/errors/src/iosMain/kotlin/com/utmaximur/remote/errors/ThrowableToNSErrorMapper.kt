package com.utmaximur.remote.errors

import platform.Foundation.NSError
import kotlin.concurrent.AtomicReference

object ThrowableToNSErrorMapper : (Throwable) -> NSError? {
    private val mapperRef: AtomicReference<((Throwable) -> NSError?)?> = AtomicReference(null)

    override fun invoke(throwable: Throwable): NSError? {
        @Suppress("MaxLineLength")
        return requireNotNull(mapperRef.value) {
            "please setup ThrowableToNSErrorMapper by call ThrowableToNSErrorMapper.setup() in iosMain"
        }.invoke(throwable)
    }

    fun setup(block: (Throwable) -> NSError?) {
        mapperRef.value = block
    }
}