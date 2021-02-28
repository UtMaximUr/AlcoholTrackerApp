package com.utmaximur.alcoholtracker.ui.calculator.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.dagger.factory.CalculatorViewModelFactory
import javax.inject.Inject

class CalculatorFragment : Fragment() {

    @Inject
    lateinit var calculatorViewModelFactory: CalculatorViewModelFactory

    private lateinit var viewModel: CalculatorViewModel

    private lateinit var button0: Button
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button

    private lateinit var buttonOk: Button
    private lateinit var buttonAc: Button
    private lateinit var buttonPlus: Button
    private lateinit var buttonMinus: Button
    private lateinit var buttonEqually: Button
    private lateinit var buttonDivide: Button
    private lateinit var buttonMultiply: Button

    private var calculatorListener: CalculatorListener? = null

    fun setListener(calculatorListener: CalculatorListener) {
        this.calculatorListener = calculatorListener
    }

    interface CalculatorListener {
        fun getValueCalculating(value: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_calculator, container, false)

        injectDagger()
        initViewModel()
        initUI(view)

        return view
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initViewModel() {
        val viewModel: CalculatorViewModel by viewModels {
            CalculatorViewModelFactory()
        }
        this.viewModel = viewModel
    }

    private fun initUI(view: View) {
        button0 = view.findViewById(R.id.calculator_button_0)
        button1 = view.findViewById(R.id.calculator_button_1)
        button2 = view.findViewById(R.id.calculator_button_2)
        button3 = view.findViewById(R.id.calculator_button_3)
        button4 = view.findViewById(R.id.calculator_button_4)
        button5 = view.findViewById(R.id.calculator_button_5)
        button6 = view.findViewById(R.id.calculator_button_6)
        button7 = view.findViewById(R.id.calculator_button_7)
        button8 = view.findViewById(R.id.calculator_button_8)
        button9 = view.findViewById(R.id.calculator_button_9)

        buttonOk = view.findViewById(R.id.calculator_button_ok)
        buttonAc = view.findViewById(R.id.calculator_button_ac)
        buttonPlus = view.findViewById(R.id.calculator_button_plus)
        buttonMinus = view.findViewById(R.id.calculator_button_minus)
        buttonEqually = view.findViewById(R.id.calculator_button_equally)
        buttonDivide = view.findViewById(R.id.calculator_button_divide)
        buttonMultiply = view.findViewById(R.id.calculator_button_multiply)

        button0.setOnClickListener {
            viewModel.setValue(0)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        button1.setOnClickListener {
            viewModel.setValue(1)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        button2.setOnClickListener {
            viewModel.setValue(2)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        button3.setOnClickListener {
            viewModel.setValue(3)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        button4.setOnClickListener {
            viewModel.setValue(4)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        button5.setOnClickListener {
            viewModel.setValue(5)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        button6.setOnClickListener {
            viewModel.setValue(6)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        button7.setOnClickListener {
            viewModel.setValue(7)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        button8.setOnClickListener {
            viewModel.setValue(8)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        button9.setOnClickListener {
            viewModel.setValue(9)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        buttonOk.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }

        buttonAc.setOnClickListener {
            viewModel.acCalculation()
        }

        buttonPlus.setOnClickListener {
            viewModel.setCurrentAction(ADDITION)
        }

        buttonMinus.setOnClickListener {
            viewModel.setCurrentAction(SUBTRACTION)
        }

        buttonEqually.setOnClickListener {
            viewModel.computeCalculation()
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        buttonDivide.setOnClickListener {
            viewModel.setCurrentAction(DIVISION)
        }

        buttonMultiply.setOnClickListener {
            viewModel.setCurrentAction(MULTIPLICATION)
        }
    }
}