package com.utmaximur.detailTrack.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.detailTrack.ui.DetailTrackScreen
import com.utmaximur.detailTrack.DetailTrackComponent
import com.utmaximur.detailTrack.store.DetailTrackStore
import com.utmaximur.domain.models.TrackData
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
internal class DefaultDetailTrackComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val trackId: Long,
    @InjectedParam private val output: (DetailTrackComponent.Output) -> Unit
) : DetailTrackComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: DetailTrackStore = instanceKeeper.getStore {
        get { parametersOf(trackId) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<DetailTrackStore.State> = store.stateFlow

    override fun navigateBack() = output(DetailTrackComponent.Output.NavigateBack)

    override fun onSaveClick(trackData: TrackData) =
        store.accept(DetailTrackStore.Intent.SaveTrackData(trackData))

    override fun openCalculatorDialog() =
        output(DetailTrackComponent.Output.OpenCalculatorDialog)

    override fun openDatePickerDialog(selectedDate: String) =
        store.accept(DetailTrackStore.Intent.SelectedDate(selectedDate))

    override fun onTodayClick() = store.accept(DetailTrackStore.Intent.Today)

    override fun onDeleteClick() =
        output(DetailTrackComponent.Output.OpenConfirmDialog(trackId))

    init {
        store.labels.onEach { event ->
            when (event) {
                is DetailTrackStore.Label.DatePickerEvent ->
                    output(DetailTrackComponent.Output.OpenDatePickerDialog(event.date))

                is DetailTrackStore.Label.CloseEvent ->
                    output(DetailTrackComponent.Output.NavigateBack)
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) = DetailTrackScreen(this)

}