package com.utmaximur.datePicker.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.datePicker.DatePickerComponent
import com.utmaximur.datePicker.store.DatePickerStore
import com.utmaximur.datePicker.ui.DatePickerScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

@Factory
internal class DefaultDatePickerComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val selectedDate: Long?,
    @InjectedParam private val closeDialog: () -> Unit
) : DatePickerComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: DatePickerStore = instanceKeeper.getStore {
        get { parametersOf(selectedDate) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<DatePickerStore.State> = store.stateFlow

    override fun dismiss() = closeDialog()

    override fun handleSelectDate(date: Long?) =
        store.accept(DatePickerStore.Intent.SelectedDate(date))

    init {
        store.labels.onEach { event ->
            when (event) {
                is DatePickerStore.Label.CloseEvent -> dismiss()
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) = DatePickerScreen(this)

}