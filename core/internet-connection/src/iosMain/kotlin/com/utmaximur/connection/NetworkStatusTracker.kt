package com.utmaximur.connection

import cocoapods.Reachability.Reachability
import cocoapods.Reachability.ReachableViaWWAN
import cocoapods.Reachability.ReachableViaWiFi
import com.utmaximur.core.logging.Logger
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.koin.core.annotation.Single

@Single
internal class NetworkStatusTracker(logger: Logger) : ObserveNetworkStatus {

    @OptIn(ExperimentalForeignApi::class)
    private val reachability: Reachability? by lazy {
        Reachability.reachabilityForInternetConnection()
    }

    private val localLogger = logger

    @OptIn(ExperimentalForeignApi::class)
    override fun invoke(): Flow<NetworkStatus> = callbackFlow {
        val currentNetworkState = reachability?.currentReachabilityStatus().asNetworkState()
        trySend(currentNetworkState)

        reachability?.reachableBlock = { r ->
            val networkState = r?.currentReachabilityStatus().asNetworkState()
            localLogger.i { "networkStatus $networkState" }
            trySend(networkState)
        }

        reachability?.unreachableBlock = { _ ->
            localLogger.i { "onUnavailable" }
            trySend(NetworkStatus.Unavailable)
        }

        reachability?.startNotifier()
        awaitClose {
            reachability?.stopNotifier()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun cocoapods.Reachability.NetworkStatus?.asNetworkState() = when (this) {
        ReachableViaWWAN -> NetworkStatus.Available
        ReachableViaWiFi -> NetworkStatus.Available
        else -> NetworkStatus.Unavailable
    }
}