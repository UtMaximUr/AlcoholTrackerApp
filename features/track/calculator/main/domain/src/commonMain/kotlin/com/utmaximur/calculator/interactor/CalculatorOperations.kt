package com.utmaximur.calculator.interactor

import com.github.murzagalin.evaluator.Evaluator
import com.utmaximur.domain.Interactor
import org.koin.core.annotation.Factory

@Factory
internal class CalculatorOperations(evaluator: Lazy<Evaluator>) : Interactor<String, String>() {
    private val evaluator by evaluator
    override suspend fun doWork(params: String): String {
        return evaluator.evaluateDouble(params)
            .toFloat()
            .toString()
            .removeSuffix(".0")
    }
}