package com.utmaximur.alcohol_calculator.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.utmaximur.alcohol_calculator.analytic_events.OpenScreenEvent
import com.utmaximur.alcohol_calculator.interactor.BACCalculate
import com.utmaximur.alcohol_calculator.models.DrinksData
import com.utmaximur.alcohol_calculator.models.Gender
import com.utmaximur.alcohol_calculator.store.AlcoholCalculatorStore.Intent
import com.utmaximur.alcohol_calculator.store.AlcoholCalculatorStore.Label
import com.utmaximur.alcohol_calculator.store.AlcoholCalculatorStore.State
import com.utmaximur.analytics.domain.AnalyticsManager
import com.utmaximur.settingsManager.UserSettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal sealed interface Message {
    data class UpdateHeight(val height: Int) : Message
    data class UpdateWeight(val weight: Int) : Message
    data class UpdateGender(val gender: Gender) : Message
    data class UpdateDrinksCount(val drinksCount: Int) : Message
}

internal class AlcoholCalculatorExecutor(
    private val userSettingsManager: UserSettingsManager,
    private val interactor: BACCalculate,
    private val analyticsManager: AnalyticsManager,
) : CoroutineExecutor<Intent, Unit, State, Message, Label>() {

    private val drinksCountFlow = MutableStateFlow(1)

    override fun executeAction(action: Unit) {
        trackScreenOpen()
        observeUserSettings()
        observeDrinks()
    }

    override fun executeIntent(intent: Intent): Unit =
        when (intent) {
            is Intent.AddDrink -> handleAddDrink()
            is Intent.Calculate -> processCalculation(intent.drinksData)
            is Intent.ChangeGender -> saveGender(intent.gender)
            is Intent.ChangeHeight -> saveHeight(intent.height)
            is Intent.ChangeWeight -> saveWeight(intent.weight)
        }

    private fun trackScreenOpen() = scope.launch {
        analyticsManager.trackEvent(OpenScreenEvent())
    }

    private fun observeUserSettings() {
        listOf(
            userSettingsManager.heightStateStream to Message::UpdateHeight,
            userSettingsManager.weightStateStream to Message::UpdateWeight,
        ).forEach { (flow, mapper) ->
            flow.onEach { value -> dispatch(mapper(value)) }.launchIn(scope)
        }
        userSettingsManager.genderStateStream
            .onEach { gender -> dispatch(Message.UpdateGender(Gender.findGender(gender))) }
            .launchIn(scope)
    }

    private fun observeDrinks() = drinksCountFlow
        .onEach { dispatch(Message.UpdateDrinksCount(it)) }
        .launchIn(scope)

    private fun handleAddDrink() = drinksCountFlow.update { it + 1 }

    private fun processCalculation(drinksData: DrinksData) {
        scope.launch {
            interactor
                .doWork(drinksData.drinks)
                .firstOrNull()
                ?.let { result -> publish(Label.ResultEvent(result)) }
        }
    }

    private fun saveWeight(weight: String) = withCheckValue(value = weight) {
        userSettingsManager.saveWeight(it)
    }

    private fun saveHeight(height: String) = withCheckValue(value = height) {
        userSettingsManager.saveHeight(it)
    }

    private fun saveGender(gender: Gender) {
        scope.launch { userSettingsManager.saveGender(gender.name) }
    }

    private inline fun withCheckValue(value: String, crossinline block: suspend (Int) -> Unit) {
        value.toIntOrNull()?.let { safeValue ->
            scope.launch { block(safeValue) }
        }
    }
}