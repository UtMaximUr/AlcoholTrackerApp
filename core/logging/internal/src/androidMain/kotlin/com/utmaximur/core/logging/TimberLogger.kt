package com.utmaximur.core.logging

import org.koin.core.annotation.Single
import com.utmaximur.app.base.app.ApplicationInfo
import com.utmaximur.app.base.app.Flavor
import timber.log.Timber
@Single
internal class TimberLogger(applicationInfo: ApplicationInfo) : Logger {

    init {
        if (applicationInfo.debugBuild || applicationInfo.flavor == Flavor.Qa) {
            Timber.plant(TiviDebugTree())
        }
    }

    override fun v(throwable: Throwable?, message: () -> String) {
        Timber.v(throwable, message())
    }

    override fun d(throwable: Throwable?, message: () -> String) {
        Timber.d(throwable, message())
    }

    override fun i(throwable: Throwable?, message: () -> String) {
        Timber.i(throwable, message())
    }

    override fun e(throwable: Throwable?, message: () -> String) {
        Timber.e(throwable, message())
    }

    override fun w(throwable: Throwable?, message: () -> String) {
        Timber.w(throwable, message())
    }
}

/**
 * Special version of [Timber.DebugTree] which is tailored for Timber being wrapped
 * within another class.
 */
private class TiviDebugTree : Timber.DebugTree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, createClassTag(), message, t)
    }

    private fun createClassTag(): String {
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            throw IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?")
        }
        var tag = stackTrace[CALL_STACK_INDEX].className
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1)
        // Tag length limit was removed in API 24.
        return tag
    }

    companion object {
        private const val MAX_TAG_LENGTH = 23
        private const val CALL_STACK_INDEX = 7
        private val ANONYMOUS_CLASS by lazy { "(\\$\\d+)+$".toPattern() }
    }
}