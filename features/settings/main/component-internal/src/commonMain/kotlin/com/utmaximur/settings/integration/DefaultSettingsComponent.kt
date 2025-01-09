package com.utmaximur.settings.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.settings.SettingsComponent
import com.utmaximur.settings.store.SettingsStore
import com.utmaximur.settings.ui.SettingsScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultSettingsComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val output: (SettingsComponent.Output) -> Unit
) : SettingsComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: SettingsStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<SettingsStore.State> = store.stateFlow

    override fun changeTheme(checked: Boolean) =
        store.accept(SettingsStore.Intent.ActiveDarkTheme(checked))

    override fun openCurrencyDialog() =
        output(SettingsComponent.Output.OpenSelectCurrencyDialog)

    @Composable
    override fun Render(modifier: Modifier) = SettingsScreen(modifier, this)

}