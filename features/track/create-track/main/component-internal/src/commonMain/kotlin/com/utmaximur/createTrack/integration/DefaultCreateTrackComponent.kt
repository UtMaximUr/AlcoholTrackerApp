package com.utmaximur.createTrack.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.createTrack.CreateTrackComponent
import com.utmaximur.createTrack.store.CreateTrackStore
import com.utmaximur.createTrack.ui.CreateTrackScreen
import com.utmaximur.domain.models.TrackData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultCreateTrackComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val output: (CreateTrackComponent.Output) -> Unit
) : CreateTrackComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: CreateTrackStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CreateTrackStore.State> = store.stateFlow

    override fun navigateBack() = output(CreateTrackComponent.Output.NavigateBack)

    override fun onSaveClick(trackData: TrackData) =
        store.accept(CreateTrackStore.Intent.SaveTrackData(trackData))

    override fun openCalculatorDialog() =
        output(CreateTrackComponent.Output.OpenCalculatorDialog)

    override fun openCurrencyDialog() =
        output(CreateTrackComponent.Output.OpenCurrencyDialog)

    override fun openDatePickerDialog(selectedDate: String) =
        store.accept(CreateTrackStore.Intent.SelectedDate(selectedDate))

    override fun onTodayClick() = store.accept(CreateTrackStore.Intent.Today)

    override fun navigateToCreateDrink() =
        output(CreateTrackComponent.Output.NavigateToCreateDrink)

    override fun onDeleteClick(id: Long) =
        output(CreateTrackComponent.Output.OpenConfirmDialog(id))

    init {
        store.labels.onEach { event ->
            when (event) {
                is CreateTrackStore.Label.DatePickerEvent ->
                    output(CreateTrackComponent.Output.OpenDatePickerDialog(event.date))

                is CreateTrackStore.Label.CloseEvent ->
                    output(CreateTrackComponent.Output.NavigateBack)
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) = CreateTrackScreen(this)

}