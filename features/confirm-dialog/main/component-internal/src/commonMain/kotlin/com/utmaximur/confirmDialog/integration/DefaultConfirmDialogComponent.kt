package com.utmaximur.confirmDialog.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.utmaximur.confirmDialog.ConfirmDialogComponent
import com.utmaximur.confirmDialog.store.ConfirmDialogStore
import com.utmaximur.confirmDialog.ui.ConfirmDialog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultConfirmDialogComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val id: Long,
    @InjectedParam private val closeDialog: () -> Unit
) : ConfirmDialogComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: ConfirmDialogStore = instanceKeeper.getStore(::get)

    override fun confirm() = store.accept(ConfirmDialogStore.Intent.Confirm(id))

    override fun dismiss() = closeDialog()

    init {
        store.labels.onEach { event ->
            when (event) {
                is ConfirmDialogStore.Label.CloseEvent -> closeDialog()
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) = ConfirmDialog(this)

}