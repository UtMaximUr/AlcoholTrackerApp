package com.utmaximur.permission.state

import androidx.compose.runtime.Stable

@Stable
internal class MutablePermissionState(
    override val isPermissionGranted: Boolean,
    private val permissionLaunch: () -> Unit,
    private val settingsLaunch: () -> Unit
) : PermissionState {

    override fun launchRequestPermission() = permissionLaunch.invoke()

    override fun openSettings() = settingsLaunch.invoke()

}