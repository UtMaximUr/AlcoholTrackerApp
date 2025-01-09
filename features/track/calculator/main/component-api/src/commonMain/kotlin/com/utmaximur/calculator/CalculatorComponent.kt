package com.utmaximur.calculator

import com.utmaximur.calculator.store.CalculatorStore
import com.utmaximur.core.decompose.ComposeDialogComponent
import kotlinx.coroutines.flow.StateFlow

interface CalculatorComponent : ComposeDialogComponent, CommandHandler {

    val model: StateFlow<CalculatorStore.State>
}