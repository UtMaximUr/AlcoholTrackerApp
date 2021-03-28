package com.utmaximur.alcoholtracker.ui.calculator

import androidx.lifecycle.ViewModel

const val ADDITION = '+'
const val SUBTRACTION = '-'
const val MULTIPLICATION = '*'
const val DIVISION = '/'

class CalculatorViewModel : ViewModel() {

    private var valueOne = ""
    private var valueTwo = ""
    private var currentValue = ""
    private var currentAction = '.'
    private var valueCalculating = 0


    fun setValue(value: Int) {
        currentValue += value.toString()
    }

    fun setCurrentAction(action: Char) {
        currentAction = action
        valueOne = currentValue
        currentValue = ""
    }

    fun getValue(): String {
        return currentValue
    }

    fun computeCalculation() {
        if (currentValue != "") {
            valueTwo = currentValue
            when (currentAction) {
                ADDITION -> {
                    valueCalculating = valueOne.toInt() + valueTwo.toInt()
                }

                SUBTRACTION -> {
                    valueCalculating = valueOne.toInt() - valueTwo.toInt()
                }

                MULTIPLICATION -> {
                    valueCalculating = valueOne.toInt() * valueTwo.toInt()
                }

                DIVISION -> {
                    valueCalculating = if (valueTwo.toInt() != 0) {
                        valueOne.toInt() / valueTwo.toInt()
                    } else {
                        0
                    }
                }
            }
            currentValue = valueCalculating.toString()
        }
    }

    fun acCalculation() {
        valueOne = ""
        valueTwo = ""
        currentValue = ""
        currentAction = '.'
    }
}