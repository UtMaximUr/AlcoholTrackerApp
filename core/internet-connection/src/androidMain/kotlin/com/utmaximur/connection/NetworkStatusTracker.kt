package com.utmaximur.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.utmaximur.core.logging.Logger
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.core.annotation.Single
import org.koin.core.component.get

@Single
internal class NetworkStatusTracker(logger: Logger) : ObserveNetworkStatus {

    private val context = get<Context>()

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val localLogger = logger

    override fun invoke(): Flow<NetworkStatus> = callbackFlow {
        localLogger.i { "network callback start" }
        trySend(connectivityManager.getCurrentNetworkState())
        val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                localLogger.i { "network onAvailable" }
                trySend(NetworkStatus.Available)
            }

            override fun onUnavailable() {
                localLogger.i { "network onUnavailable" }
                trySend(NetworkStatus.Unavailable)
            }

            override fun onLost(network: Network) {
                localLogger.i { "network onLost" }
                trySend(NetworkStatus.Unavailable)
            }
        }
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager.registerNetworkCallback(request, networkStatusCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkStatusCallback)
        }
    }.distinctUntilChanged()

    private fun ConnectivityManager.getCurrentNetworkState(): NetworkStatus {

        val networkCapabilities = getNetworkCapabilities(activeNetwork)

        return networkCapabilities?.asNetworkState() ?: NetworkStatus.Unavailable
    }

    private fun NetworkCapabilities.asNetworkState(): NetworkStatus {
        val connected = hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        val metered = !hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)

        return when {
            connected -> NetworkStatus.Available
            else -> NetworkStatus.Unavailable
        }
    }
}