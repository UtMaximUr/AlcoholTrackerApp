package com.utmaximur.alcoholtracker.presantation.calculator


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.util.ADDITION
import com.utmaximur.alcoholtracker.util.DIVISION
import com.utmaximur.alcoholtracker.util.MULTIPLICATION
import com.utmaximur.alcoholtracker.util.SUBTRACTION
import com.utmaximur.alcoholtracker.util.extension.empty
import javax.inject.Inject

class CalculatorViewModel @Inject constructor() : ViewModel() {

    private var valueOne = Int.empty()
    private var valueTwo = Int.empty()
    private var valueCalculating = Int.empty()

    private var isActionSelect = false

    private var currentAction = Char.empty()

    private val _currentValue = MutableLiveData<String>()
    val currentValue: LiveData<String> get() = _currentValue

    init {
        _currentValue.value = String.empty()
    }

    fun setValue(value: Int) {
        if (isActionSelect) {
            clearValue()
            isActionSelect = !isActionSelect
        }
        _currentValue.value += value.toString()
    }

    private fun clearValue() {
        _currentValue.value = String.empty()
    }

    fun setCurrentAction(action: Char) {
        if (currentAction != Char.empty()) {
            computeCalculation()
        }
        currentAction = action
        valueOne = currentValue.value?.toInt()!!
        isActionSelect = !isActionSelect
    }

    fun computeCalculation() {
        if (currentValue.value != String.empty()) {
            valueTwo = currentValue.value?.toInt()!!
        }
        when (currentAction) {
            ADDITION -> {
                valueCalculating = valueOne + valueTwo
            }

            SUBTRACTION -> {
                valueCalculating = valueOne - valueTwo
            }

            MULTIPLICATION -> {
                valueCalculating = valueOne * valueTwo
            }

            DIVISION -> {
                if (valueTwo != Int.empty()) {
                    valueCalculating = valueOne / valueTwo
                }
            }
        }
        _currentValue.value = valueCalculating.toString()
        currentAction = Char.empty()
    }

    fun acCalculation() {
        valueOne = Int.empty()
        valueTwo = Int.empty()
        valueCalculating = Int.empty()
        _currentValue.value = String.empty()
        currentAction = Char.empty()
    }
}