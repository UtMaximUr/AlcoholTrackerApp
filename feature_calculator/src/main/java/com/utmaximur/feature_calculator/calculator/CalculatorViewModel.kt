package com.utmaximur.feature_calculator.calculator


import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.feature_calculator.utils.ADDITION
import com.utmaximur.feature_calculator.utils.DIVISION
import com.utmaximur.feature_calculator.utils.MULTIPLICATION
import com.utmaximur.feature_calculator.utils.SUBTRACTION
import com.utmaximur.utils.empty
import com.utmaximur.utils.zero
import com.utmaximur.utils.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {

    private var valueOne = Float.zero()
    private var valueTwo = Float.zero()
    private var valueCalculating = Float.zero()

    private var isActionSelect = false

    private var currentAction = Char.empty()

    val currentValue: LiveData<String> by lazy { MutableLiveData(String.empty()) }

    fun setValue(value: Int) {
        if (isActionSelect) {
            clearValue()
            isActionSelect = !isActionSelect
        }
        // check zero
        if (currentValue.value != Int.zero().toString()) {
            // check max and min value
            if (currentValue.value != String.empty() && currentValue.value?.toFloat()!! > Float.MIN_VALUE
                && currentValue.value?.toFloat()!! < Float.MAX_VALUE
            ) {
                if (value.toString().isDigitsOnly()) {
                    (currentValue as MutableLiveData).value += value.toString()
                }
            } else {
                if (value.toString().isDigitsOnly()) {
                    (currentValue as MutableLiveData).value += value.toString()
                }
            }
        }
    }

    fun setPriceValue(price: String) {
        if (price.isNotEmpty()) {
            currentValue.setValue(price)
        }
    }

    private fun clearValue() {
        currentValue.setValue(String.empty())
    }

    fun setCurrentAction(action: Char) {
        if (currentValue.value != String.empty()) {
            if (currentAction != Char.empty()) {
                computeCalculation()
            }
            currentAction = action
            valueOne = currentValue.value?.toFloat()!!
            isActionSelect = !isActionSelect
        }
    }

    fun computeCalculation() {
        if (currentValue.value != String.empty()) {
            valueTwo = currentValue.value?.toFloat()!!
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
                if (valueTwo != Float.zero()) {
                    valueCalculating = valueOne / valueTwo
                }
            }
        }
        currentValue.setValue(valueCalculating.toString())
        currentAction = Char.empty()
    }

    fun acCalculation() {
        valueOne = Float.zero()
        valueTwo = Float.zero()
        valueCalculating = Float.zero()
        currentValue.setValue(String.empty())
        currentAction = Char.empty()
    }
}