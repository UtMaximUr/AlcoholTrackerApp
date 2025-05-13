package com.utmaximur.createTrack.integration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.bottombar.LocalBottomBarController
import com.utmaximur.createTrack.CreateTrackComponent
import com.utmaximur.createTrack.store.CreateTrackStore
import com.utmaximur.createTrack.ui.CreateTrackScreen
import com.utmaximur.domain.TrackData
import com.utmaximur.geocoder.GeocoderComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parameterArrayOf

@Factory
internal class DefaultCreateTrackComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val output: (CreateTrackComponent.Output) -> Unit,
) : CreateTrackComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: CreateTrackStore = instanceKeeper.getStore(::get)
    private val trackBuilder = TrackData.Builder()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CreateTrackStore.State> = store.stateFlow

    override val geocoderComponent: GeocoderComponent by lazy {
        get { parameterArrayOf(childContext(GeocoderComponent::class.simpleName.orEmpty())) }
    }

    override fun navigateBack() = output(CreateTrackComponent.Output.NavigateBack)

    override fun onSaveClick() =
        store.accept(CreateTrackStore.Intent.SaveTrackData(trackBuilder.build()))

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
                is CreateTrackStore.Label.DateConfirmed ->
                    output(CreateTrackComponent.Output.OpenDatePickerDialog(event.date))

                is CreateTrackStore.Label.DateSelected ->
                    trackBuilder.setDate(event.date)

                is CreateTrackStore.Label.TrackLinked ->
                    geocoderComponent.savePlaceToTrack(event.trackId)

                is CreateTrackStore.Label.CloseEvent ->
                    output(CreateTrackComponent.Output.NavigateBack)
            }
        }.launchIn(coroutineScope())
    }

    @Composable
    override fun Render(modifier: Modifier) {
        val bottomBarController = LocalBottomBarController.current
        LaunchedEffect(Unit) {
            bottomBarController.hideToLifecycle(lifecycle)
        }
        CreateTrackScreen(this, trackBuilder)
    }
}
