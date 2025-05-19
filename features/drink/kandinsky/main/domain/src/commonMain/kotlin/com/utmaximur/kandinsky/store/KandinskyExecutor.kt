package com.utmaximur.kandinsky.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.connection.ObserveNetworkStatus
import com.utmaximur.connection.map
import com.utmaximur.core.mvi_mapper.Request
import com.utmaximur.core.mvi_mapper.asRequest
import com.utmaximur.domain.actions.PathFileProviderData
import com.utmaximur.domain.kandinsky.GenerationResult
import com.utmaximur.domain.kandinsky.ImageStyle
import com.utmaximur.domain.kandinsky.KandinskyRepository
import com.utmaximur.kandinsky.GenerateImageData
import com.utmaximur.kandinsky.analytic_events.GenerationImageEvent
import com.utmaximur.kandinsky.analytic_events.OpenScreenEvent
import com.utmaximur.kandinsky.interactor.GenerateImage
import com.utmaximur.kandinsky.interactor.GetStyles
import com.utmaximur.kandinsky.store.KandinskyScreenStore.Intent
import com.utmaximur.kandinsky.store.KandinskyScreenStore.Label
import com.utmaximur.kandinsky.store.KandinskyScreenStore.State
import com.utmaximur.kandinsky.validation.RequestValidator
import com.utmaximur.message.models.MessageContainer
import com.utmaximur.message.models.MessageService
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal sealed interface Message {
    data class UpdateNetworkStatus(val available: Boolean) : Message
    data class UpdateStyles(val requestStylesUi: Request<List<ImageStyle>>) : Message
    data class UpdateGenerationResult(val generationResult: GenerationResult) : Message
}

internal class KandinskyExecutor(
    private val repository: KandinskyRepository,
    private val requestValidator: RequestValidator,
    private val messageService: MessageService,
    private val stylesInteractor: GetStyles,
    private val generateImageInteractor: GenerateImage,
    private val providerData: PathFileProviderData,
    private val networkStatus: ObserveNetworkStatus,
    private val analyticsManager: AnalyticsManager,
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    private var stylesJob: Job? = null

    override fun executeAction(action: Unit) {
        trackScreenOpen()
        startObservingNetworkAvailability()
        fetchStyles()
        startObservingResults()
    }

    override fun executeIntent(intent: Intent) {
        when (intent) {
            is Intent.Generate -> processGeneration(intent.data)
            Intent.RetryStyles -> retryFetchStyles()
            Intent.GenerationCompletion -> handleGenerationCompletion()
            Intent.Close -> finalizeImageGeneration()
        }
    }

    private fun trackScreenOpen() = scope.launch {
        analyticsManager.trackEvent(OpenScreenEvent())
    }

    private fun startObservingNetworkAvailability() = networkStatus.invoke()
        .map(onAvailable = { true }, onUnavailable = { false })
        .onEach { available -> dispatch(Message.UpdateNetworkStatus(available)) }
        .launchIn(scope)

    private fun fetchStyles() {
        stylesJob = scope.launch {
            stylesInteractor.doWork(Unit)
                .asRequest()
                .collectLatest { styles -> dispatch(Message.UpdateStyles(styles)) }
        }
    }

    private fun retryFetchStyles() {
        stylesJob?.cancel()
        fetchStyles()
    }

    private fun startObservingResults() = repository.observeGenerationResult()
        .onEach { result -> dispatch(Message.UpdateGenerationResult(result)) }
        .launchIn(scope)

    private fun processGeneration(data: GenerateImageData) = scope.launch {
        val validatorResult = requestValidator.validate(data)
        validatorResult.errors.firstOrNull()?.let { error -> handleErrorMessage(error.message) }
            ?: run {
                val (prompt, style) = validatorResult.generateImageData
                analyticsManager.trackEvent(GenerationImageEvent(prompt = prompt, style = style))
                generateImageInteractor.invoke(validatorResult.generateImageData)
                    .onFailure { error -> handleErrorMessage(error.message) }
            }
    }

    private fun handleGenerationCompletion() = scope.launch {
        val path = state().generationResult.imagePathFile
        providerData.sendData(path)
        finalizeImageGeneration()
    }

    private fun finalizeImageGeneration() = scope.launch {
        repository.cancelAllGenerations()
        publish(Label.CloseEvent)
    }

    private fun handleErrorMessage(message: String?) {
        messageService.showMessage(MessageContainer.ErrorMessage(message))
    }
}