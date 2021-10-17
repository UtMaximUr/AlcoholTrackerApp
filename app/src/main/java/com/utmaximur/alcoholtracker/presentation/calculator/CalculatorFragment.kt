package com.utmaximur.alcoholtracker.presentation.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.databinding.FragmentCalculatorBinding
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.util.*
import com.utmaximur.alcoholtracker.util.extension.empty
import javax.inject.Inject

class CalculatorFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<CalculatorViewModel>

    private val viewModel: CalculatorViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDagger()
        initUI()
        getPriceValue()
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUI() = with(binding) {
        viewModel.currentValue.observe(viewLifecycleOwner, { value ->
            result.text = value
            findNavController().previousBackStackEntry?.savedStateHandle?.set(KEY_CALCULATOR, value)
        })

        calculatorButton0.setOnClickListener {
            viewModel.setValue(KEY_0)
        }

        calculatorButton1.setOnClickListener {
            viewModel.setValue(KEY_1)
        }

        calculatorButton2.setOnClickListener {
            viewModel.setValue(KEY_2)
        }

        calculatorButton3.setOnClickListener {
            viewModel.setValue(KEY_3)
        }

        calculatorButton4.setOnClickListener {
            viewModel.setValue(KEY_4)
        }

        calculatorButton5.setOnClickListener {
            viewModel.setValue(KEY_5)
        }

        calculatorButton6.setOnClickListener {
            viewModel.setValue(KEY_6)
        }

        calculatorButton7.setOnClickListener {
            viewModel.setValue(KEY_7)
        }

        calculatorButton8.setOnClickListener {
            viewModel.setValue(KEY_8)
        }

        calculatorButton9.setOnClickListener {
            viewModel.setValue(KEY_9)
        }

        calculatorButtonOk.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.remove<String>(
                KEY_CALCULATOR
            )
            dismiss()
        }

        calculatorButtonAc.setOnClickListener {
            viewModel.acCalculation()
        }

        calculatorButtonPlus.setOnClickListener {
            viewModel.setCurrentAction(ADDITION)
        }

        calculatorButtonMinus.setOnClickListener {
            viewModel.setCurrentAction(SUBTRACTION)
        }

        calculatorButtonEqually.setOnClickListener {
            viewModel.computeCalculation()
        }

        calculatorButtonDivide.setOnClickListener {
            viewModel.setCurrentAction(DIVISION)
        }

        calculatorButtonMultiply.setOnClickListener {
            viewModel.setCurrentAction(MULTIPLICATION)
        }
    }

    private fun getPriceValue() {
        if (arguments?.getString(PRICE_DRINK) != String.empty()) {
            viewModel.setValue(arguments?.getString(PRICE_DRINK)?.toFloat()?.toInt()!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}