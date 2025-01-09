package com.utmaximur.core.logging

interface Logger {

    /** Log a verbose exception and a message with optional format args.  */
    fun v(throwable: Throwable? = null, message: () -> String = { "" }) = Unit

    /** Log a debug exception and a message with optional format args.  */
    fun d(throwable: Throwable? = null, message: () -> String = { "" }) = Unit

    /** Log a info exception and a message with optional format args.  */
    fun i(throwable: Throwable? = null, message: () -> String = { "" }) = Unit

    /** Log an exception and a message with optional format args.  */
    fun e(throwable: Throwable? = null, message: () -> String = { "" }) = Unit

    /** Log a warning exception and a message with optional format args.  */
    fun w(throwable: Throwable? = null, message: () -> String = { "" }) = Unit
}