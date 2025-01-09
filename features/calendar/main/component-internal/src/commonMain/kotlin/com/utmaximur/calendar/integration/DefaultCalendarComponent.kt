package com.utmaximur.calendar.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.utmaximur.calendar.CalendarComponent
import com.utmaximur.calendar.store.CalendarStore
import com.utmaximur.calendar.ui.CalendarScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Factory
internal class DefaultCalendarComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val output: (CalendarComponent.Output) -> Unit
) : CalendarComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val store: CalendarStore = instanceKeeper.getStore(::get)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<CalendarStore.State> = store.stateFlow

    override fun onCreateTrackClick() =
        output(CalendarComponent.Output.NavigateCreateTrack)

    override fun toggleView() = store.accept(CalendarStore.Intent.ToggleCalendarView)

    override fun onTrackClick(trackId: Long) =
        output(CalendarComponent.Output.NavigateDetailTrack(trackId))

    @Composable
    override fun Render(modifier: Modifier) = CalendarScreen(modifier, this)

}