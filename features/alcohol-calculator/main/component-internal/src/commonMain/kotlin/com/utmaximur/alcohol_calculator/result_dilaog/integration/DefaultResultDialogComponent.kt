package com.utmaximur.alcohol_calculator.result_dilaog.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.utmaximur.alcohol_calculator.models.CalculateResult
import com.utmaximur.alcohol_calculator.result_dilaog.ResultDialog
import com.utmaximur.alcohol_calculator.result_dilaog.ResultDialogComponent
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class DefaultResultDialogComponent(
    @InjectedParam componentContext: ComponentContext,
    @InjectedParam private val calculateResult: CalculateResult,
    @InjectedParam private val dismissCallback: () -> Unit,
) : ResultDialogComponent, ComponentContext by componentContext {

    override fun dismiss() = dismissCallback()

    @Composable
    override fun Render(modifier: Modifier) = ResultDialog(this, calculateResult)
}