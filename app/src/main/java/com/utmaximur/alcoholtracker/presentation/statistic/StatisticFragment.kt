package com.utmaximur.alcoholtracker.presentation.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.accompanist.pager.ExperimentalPagerApi
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.presentation.splash.ui.theme.AlcoholTrackerTheme
import com.utmaximur.alcoholtracker.presentation.statistic.ui.StatisticView
import javax.inject.Inject

class StatisticFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<StatisticViewModel>

    private val viewModel: StatisticViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    @ExperimentalPagerApi
    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDagger()
        return ComposeView(requireContext()).apply {
            setContent {
                AlcoholTrackerTheme {
                    Surface(color = colorResource(id = R.color.background_color)) {
                        StatisticView(viewModel)
                    }
                }
            }
        }
    }
}
