package com.utmaximur.alcoholtracker.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.databinding.FragmentCalculatorBinding
import com.utmaximur.alcoholtracker.di.factory.CalculatorViewModelFactory
import com.utmaximur.alcoholtracker.util.*
import javax.inject.Inject

class CalculatorFragment : Fragment() {

    @Inject
    lateinit var calculatorViewModelFactory: CalculatorViewModelFactory

    private lateinit var viewModel: CalculatorViewModel
    private lateinit var binding: FragmentCalculatorBinding

    private var calculatorListener: CalculatorListener? = null

    fun setListener(calculatorListener: CalculatorListener) {
        this.calculatorListener = calculatorListener
    }

    interface CalculatorListener {
        fun getValueCalculating(value: String)
        fun closeCalculator()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculatorBinding.inflate(layoutInflater)
        injectDagger()
        initViewModel()
        initUI()
        return binding.root
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

    private fun initUI() {
        binding.calculatorButton0.setOnClickListener {
            viewModel.setValue(KEY_0)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButton1.setOnClickListener {
            viewModel.setValue(KEY_1)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButton2.setOnClickListener {
            viewModel.setValue(KEY_2)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButton3.setOnClickListener {
            viewModel.setValue(KEY_3)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButton4.setOnClickListener {
            viewModel.setValue(KEY_4)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButton5.setOnClickListener {
            viewModel.setValue(KEY_5)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButton6.setOnClickListener {
            viewModel.setValue(KEY_6)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButton7.setOnClickListener {
            viewModel.setValue(KEY_7)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButton8.setOnClickListener {
            viewModel.setValue(KEY_8)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButton9.setOnClickListener {
            viewModel.setValue(KEY_9)
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButtonOk.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.remove(this)
                ?.commit()
            calculatorListener?.closeCalculator()
        }

        binding.calculatorButtonAc.setOnClickListener {
            viewModel.acCalculation()
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButtonPlus.setOnClickListener {
            viewModel.setCurrentAction(ADDITION)
        }

        binding.calculatorButtonMinus.setOnClickListener {
            viewModel.setCurrentAction(SUBTRACTION)
        }

        binding.calculatorButtonEqually.setOnClickListener {
            viewModel.computeCalculation()
            calculatorListener?.getValueCalculating(viewModel.getValue())
        }

        binding.calculatorButtonDivide.setOnClickListener {
            viewModel.setCurrentAction(DIVISION)
        }

        binding.calculatorButtonMultiply.setOnClickListener {
            viewModel.setCurrentAction(MULTIPLICATION)
        }

        getPriceDrink()
    }

    private fun getPriceDrink() {
        if (arguments?.getString(PRICE_DRINK) != "") {
            arguments?.getString(PRICE_DRINK)?.toDouble()?.toInt()?.let { viewModel.setValue(it) }
        }
    }
}