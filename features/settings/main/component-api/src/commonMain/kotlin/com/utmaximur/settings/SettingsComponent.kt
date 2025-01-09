package com.utmaximur.settings

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.settings.store.SettingsStore
import kotlinx.coroutines.flow.StateFlow

interface SettingsComponent : ComposeComponent {

    val model: StateFlow<SettingsStore.State>

    fun changeTheme(checked: Boolean)

    fun openCurrencyDialog()

    sealed class Output {
        data object OpenSelectCurrencyDialog : Output()
    }
}