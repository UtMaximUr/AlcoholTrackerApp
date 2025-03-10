package com.utmaximur.connection

import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

interface ObserveNetworkStatus : KoinComponent {

    operator fun invoke(): Flow<NetworkStatus>
}
