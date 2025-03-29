package com.utmaximur.workmanager.constraint

import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_monitor_t
import platform.Network.nw_path_monitor_update_handler_t
import platform.Network.nw_path_status_satisfied
import platform.Network.nw_path_t
import platform.darwin.dispatch_queue_create
import com.utmaximur.workmanager.IOSCommonWorkManagerOperation
import com.utmaximur.workmanager.SimpleWorkManager
import com.utmaximur.workmanager.dsl.CommonConstraints

internal actual object ConnectivityCheck : ConstraintCheck {
    private var hasInternet: Boolean = false

    private val observer = AppleNetworkObserver()

    init {
        observer.setListener(
            object : NetworkObserver.Listener {
                override fun networkChanged(isOnline: Boolean) {
                    hasInternet = isOnline
// TODO()
//                    if (isOnline && SimpleWorkManager::applicationContainer.isInitialized) {
//                        (SimpleWorkManager.commonWorkManagerOperation as IOSCommonWorkManagerOperation).constraintChanged()
//                    }
                }
            }
        )
    }

    actual override suspend fun match(commonConstraints: CommonConstraints): Boolean {
        if (!commonConstraints.requireNetwork)
            return true

        return hasInternet
    }

}

internal class AppleNetworkObserver : NetworkObserver, nw_path_monitor_update_handler_t {
    var monitor: nw_path_monitor_t = null
    var listener: NetworkObserver.Listener? = null

    fun close() {
        if (monitor != null) {
            nw_path_monitor_cancel(monitor)
        }
    }

    override fun setListener(listener: NetworkObserver.Listener) {
        check(monitor == null) {
            "Apollo: there can be only one listener"
        }
        monitor = nw_path_monitor_create()
        this.listener = listener
        nw_path_monitor_set_update_handler(monitor, this)
        nw_path_monitor_set_queue(monitor, dispatch_queue_create("NWPath", null))
        nw_path_monitor_start(monitor)
    }

    override fun invoke(p1: nw_path_t) {
        listener?.networkChanged((nw_path_get_status(p1) == nw_path_status_satisfied))
    }
}

internal interface NetworkObserver {
    /**
     * Sets the listener
     *
     * Implementation must call [listener] shortly after [setListener] returns to let the callers know about the initial state.
     */
    fun setListener(listener: Listener)

    interface Listener {
        fun networkChanged(isOnline: Boolean)
    }
}

internal val NoOpNetworkObserver = object : NetworkObserver {
    override fun setListener(listener: NetworkObserver.Listener) {
        listener.networkChanged(true)
    }
}
