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
import com.utmaximur.domain.actions.PlatformFileProviderData
import com.utmaximur.domain.createDrink.CreateDrinkRepository
import com.utmaximur.domain.models.Icon
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.models.MessageService
import createDrink.domain.resources.Res
import createDrink.domain.resources.saving_error
import createDrink.domain.resources.successful_save
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString


internal sealed interface Message {
    data class UpdateIcons(val icons: List<Icon>) : Message
    data class UpdateImageUri(val path: String) : Message
}

internal class CreateDrinkExecutor(
    private val analyticsManager: AnalyticsManager,
    private val providerData: PlatformFileProviderData,
    private val messageService: MessageService,
    private val drinkValidator: DrinkValidator,
    private val createDrinkRepository: CreateDrinkRepository,
    private val interactor: CreateDrink
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    private var savingJob: Job? = null

    override fun executeAction(action: Unit) {
        scope.launch { analyticsManager.trackEvent(OpenScreenEvent()) }
        createDrinkRepository.iconsStream
            .onEach { icons -> dispatch(Message.UpdateIcons(icons)) }
            .launchIn(scope)
        providerData.dataFlow
            .onEach { url -> dispatch(Message.UpdateImageUri(url)) }
            .launchIn(scope)
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.SaveDrinkData -> savingJob = scope.launch {
                handleDrinkData(intent.drinkData)
            }
        }
    }

    private suspend fun handleDrinkData(drinkData: DrinkData) {
        val validator = drinkValidator.validate(drinkData)
        validator.errors.firstOrNull()?.let { error ->
            showMessage(error.message)
            savingJob?.cancel()
        }
        if (validator.errors.isEmpty()) {
            analyticsManager.trackEvent(SaveDrinkEvent(validator.drinkData.name))
            interactor.invoke(validator.drinkData)
                .onFailure {
                    showMessage(Res.string.saving_error, it.message)
                    savingJob?.cancel()
                }
                .onSuccess {
                    showMessage(Res.string.successful_save)
                    publish(Label.CloseEvent)
                }
        }
    }

    private suspend fun showMessage(res: StringResource, args: String? = null) {
        val message = getString(res, args.orEmpty())
        messageService.showMessage(MessageContainer.SimpleMessage(message))
    }
}