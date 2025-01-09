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
import createDrink.domain.resources.successful_save
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
            is Intent.SaveDrinkData -> handleDrinkData(intent.drinkData)
        }
    }

    private fun handleDrinkData(drinkData: DrinkData) {
        val validator = drinkValidator.validate(drinkData)
        validator.errors.firstOrNull()?.let { error ->
            showMessage(error.message)
        }
        if (validator.errors.isEmpty()) {
            scope.launch {
                analyticsManager.trackEvent(SaveDrinkEvent(drinkData.name))
                interactor.doWork(drinkData)
                showMessage(Res.string.successful_save)
                publish(Label.CloseEvent)
            }
        }
    }

    private fun showMessage(message: StringResource){
        scope.launch {
            messageService.showMessage(MessageContainer.SimpleMessage(getString(message)))
        }
    }
}