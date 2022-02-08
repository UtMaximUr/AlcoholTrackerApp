package com.utmaximur.alcoholtracker.presentation.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.presentation.calculator.ui.CalculatorView
import com.utmaximur.alcoholtracker.presentation.splash.ui.theme.AlcoholTrackerTheme
import com.utmaximur.alcoholtracker.util.KEY_CALCULATOR
import com.utmaximur.alcoholtracker.util.PRICE_DRINK
import com.utmaximur.alcoholtracker.util.extension.empty
import com.utmaximur.alcoholtracker.util.removeNavigationResult
import com.utmaximur.alcoholtracker.util.setNavigationResult
import javax.inject.Inject

class CalculatorFragment : DialogFragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<CalculatorViewModel>

    private val viewModel: CalculatorViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AlcoholTrackerTheme {
                    Surface(color = colorResource(id = R.color.background_color)) {
                        CalculatorView(
                            viewModel = viewModel,
                            onOkClick = {
                                this@CalculatorFragment.removeNavigationResult<String>(
                                    KEY_CALCULATOR
                                )
                                this@CalculatorFragment.dismiss()
                            },
                            onDismiss = { this@CalculatorFragment.dismiss() })
                    }
                }
            }
        }
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
        viewModel.currentValue.observe(viewLifecycleOwner) { value ->
            this@CalculatorFragment.setNavigationResult(KEY_CALCULATOR, value, false)
        }
    }

    private fun getPriceValue() {
        if (arguments?.getString(PRICE_DRINK) != String.empty()) {
            viewModel.setPriceValue(arguments?.getString(PRICE_DRINK)?.toFloat()?.toInt()!!)
        }
    }
}