package com.utmaximur.alcoholtracker.presantation.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.databinding.FragmentCalculatorBinding
import com.utmaximur.alcoholtracker.presantation.base.BaseViewModelFactory
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

    private fun initUI() {
        viewModel.currentValue.observe(viewLifecycleOwner, { value ->
            binding.result.text = value
            calculatorListener?.getValueCalculating(value)
        })

        binding.calculatorButton0.setOnClickListener {
            viewModel.setValue(KEY_0)
        }

        binding.calculatorButton1.setOnClickListener {
            viewModel.setValue(KEY_1)
        }

        binding.calculatorButton2.setOnClickListener {
            viewModel.setValue(KEY_2)
        }

        binding.calculatorButton3.setOnClickListener {
            viewModel.setValue(KEY_3)
        }

        binding.calculatorButton4.setOnClickListener {
            viewModel.setValue(KEY_4)
        }

        binding.calculatorButton5.setOnClickListener {
            viewModel.setValue(KEY_5)
        }

        binding.calculatorButton6.setOnClickListener {
            viewModel.setValue(KEY_6)
        }

        binding.calculatorButton7.setOnClickListener {
            viewModel.setValue(KEY_7)
        }

        binding.calculatorButton8.setOnClickListener {
            viewModel.setValue(KEY_8)
        }

        binding.calculatorButton9.setOnClickListener {
            viewModel.setValue(KEY_9)
        }

        binding.calculatorButtonOk.setOnClickListener {
            dismiss()
        }

        binding.calculatorButtonAc.setOnClickListener {
            viewModel.acCalculation()
        }

        binding.calculatorButtonPlus.setOnClickListener {
            viewModel.setCurrentAction(ADDITION)
        }

        binding.calculatorButtonMinus.setOnClickListener {
            viewModel.setCurrentAction(SUBTRACTION)
        }

        binding.calculatorButtonEqually.setOnClickListener {
            viewModel.computeCalculation()
        }

        binding.calculatorButtonDivide.setOnClickListener {
            viewModel.setCurrentAction(DIVISION)
        }

        binding.calculatorButtonMultiply.setOnClickListener {
            viewModel.setCurrentAction(MULTIPLICATION)
        }
    }

    private fun getPriceValue() {
        if (arguments?.getString(PRICE_DRINK) != String.empty()) {
            viewModel.setValue(arguments?.getString(PRICE_DRINK)?.toInt()!!)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}