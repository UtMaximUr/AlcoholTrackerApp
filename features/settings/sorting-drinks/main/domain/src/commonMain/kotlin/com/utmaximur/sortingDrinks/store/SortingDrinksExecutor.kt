package com.utmaximur.sortingDrinks.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.domain.sortingDrinks.SortingDrink
import com.utmaximur.domain.sortingDrinks.SortingDrinksRepository
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.models.MessageService
import com.utmaximur.sortingDrinks.analytic_events.OpenScreenEvent
import com.utmaximur.sortingDrinks.interactor.UpdateSortedDrinks
import com.utmaximur.sortingDrinks.store.SortingDrinksStore.Intent
import com.utmaximur.sortingDrinks.store.SortingDrinksStore.Label
import com.utmaximur.sortingDrinks.store.SortingDrinksStore.State
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


internal sealed interface Message {
    data class UpdateSortedDrinks(val sortedDrinks: List<SortingDrink>) : Message
}

internal class SortingDrinksExecutor(
    private val repository: SortingDrinksRepository,
    private val interactor: UpdateSortedDrinks,
    private val messageService: MessageService,
    private val analyticsManager: AnalyticsManager,
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    override fun executeAction(action: Unit) {
        trackScreenOpen()
        observeDrinks()
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            Intent.SaveSortingDrinks -> saveSortedDrinks()
            is Intent.Move -> handleMoveDrinks(intent.from, intent.to)
        }
    }

    private fun trackScreenOpen() = scope.launch {
        analyticsManager.trackEvent(OpenScreenEvent())
    }

    private fun observeDrinks() = repository.sortingDrinksStream
        .onEach { sortedDrinks -> dispatch(Message.UpdateSortedDrinks(sortedDrinks)) }
        .launchIn(scope)

    private fun handleMoveDrinks(from: Int, to: Int) {
        val updatedSortedDrinks = state().sortedDrinks.toMutableList().apply {
            add(to, removeAt(from))
        }
        dispatch(Message.UpdateSortedDrinks(updatedSortedDrinks))
    }

    private fun saveSortedDrinks() = scope.launch {
        interactor.invoke(state().sortedDrinks)
            .onSuccess { handleSaveSuccess() }
            .onFailure { error -> handleSaveError(error) }
    }

    private fun handleSaveError(error: Throwable) {
        messageService.showMessage(MessageContainer.ErrorMessage(error.message))
    }

    private fun handleSaveSuccess() {
        messageService.showMessage(MessageContainer.SuccessfulUpdateMessage)
        publish(Label.CloseEvent)
    }
}