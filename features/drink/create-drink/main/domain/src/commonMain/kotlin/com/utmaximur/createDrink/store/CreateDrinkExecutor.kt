package com.utmaximur.createDrink.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.createDrink.DrinkData
import com.utmaximur.createDrink.analytic_events.OpenScreenEvent
import com.utmaximur.createDrink.analytic_events.SaveDrinkEvent
import com.utmaximur.createDrink.interactor.CreateDrink
import com.utmaximur.createDrink.store.CreateDrinkStore.Intent
import com.utmaximur.createDrink.store.CreateDrinkStore.Label
import com.utmaximur.createDrink.store.CreateDrinkStore.State
import com.utmaximur.createDrink.validation.DrinkValidator
import com.utmaximur.domain.actions.PathFileProviderData
import com.utmaximur.domain.createDrink.CreateDrinkRepository
import com.utmaximur.domain.createDrink.Icon
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.models.MessageService
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


internal sealed interface Message {
    data class UpdateIcons(val icons: List<Icon>) : Message
    data class UpdateImageUri(val path: String) : Message
}

internal class CreateDrinkExecutor(
    private val analyticsManager: AnalyticsManager,
    private val providerData: PathFileProviderData,
    private val messageService: MessageService,
    private val drinkValidator: DrinkValidator,
    private val createDrinkRepository: CreateDrinkRepository,
    private val interactor: CreateDrink
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    private var savingJob: Job? = null

    override fun executeAction(action: Unit) {
        trackScreenOpen()
        observeIcons()
        observeImageData()
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.SaveDrinkData -> saveDrink(intent.drinkData)
        }
    }

    private fun trackScreenOpen() = scope.launch {
        analyticsManager.trackEvent(OpenScreenEvent())
    }

    private fun observeIcons() = createDrinkRepository.iconsStream
        .onEach { icons -> dispatch(Message.UpdateIcons(icons)) }
        .launchIn(scope)

    private fun observeImageData() = providerData.dataFlow
        .onEach { url -> dispatch(Message.UpdateImageUri(url)) }
        .launchIn(scope)

    private fun saveDrink(drinkData: DrinkData) =
        validateAndProcessDrinkData(drinkData) { data ->
            analyticsManager.trackEvent(SaveDrinkEvent(data.name))
            interactor.invoke(data)
                .onFailure { error -> handleErrorMessage(error.message) }
                .onSuccess { handleSaveSuccess() }
        }

    private fun validateAndProcessDrinkData(data: DrinkData, block: suspend (DrinkData) -> Unit) {
        savingJob?.cancel()
        savingJob = scope.launch {
            val validatorResult = drinkValidator.validate(data)
            validatorResult.errors.firstOrNull()?.let { error -> handleErrorMessage(error.message) }
                ?: block(validatorResult.drinkData)
        }
    }

    private fun handleSaveSuccess() {
        messageService.showMessage(MessageContainer.SuccessfulSaveMessage)
        publish(Label.CloseEvent)
    }

    private fun handleErrorMessage(message: String?) {
        messageService.showMessage(MessageContainer.ErrorMessage(message))
        savingJob?.cancel()
    }
}