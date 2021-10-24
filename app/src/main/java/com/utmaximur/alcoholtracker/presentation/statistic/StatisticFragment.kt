package com.utmaximur.alcoholtracker.presentation.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.databinding.FragmentStatisticBinding
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.presentation.statistic.adapter.CountDaysAdapter
import com.utmaximur.alcoholtracker.presentation.statistic.adapter.StatisticAdapter
import com.utmaximur.alcoholtracker.presentation.statistic.adapter.TopDrinkAdapter
import javax.inject.Inject

class StatisticFragment :
    Fragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<StatisticViewModel>

    private val viewModel: StatisticViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDagger()
        initUI()
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUI() = with(binding) {
        viewModel.getStatistics()

        topDrinksList.layoutManager = GridLayoutManager(context, 4)
        val adapter =  TopDrinkAdapter()
        topDrinksList.adapter = adapter

        viewModel.statisticsDrinksList.observe(viewLifecycleOwner, { drinksList ->
            adapter.submitList(drinksList)
        })

        viewModel.statisticsPriceByPeriod.observe(viewLifecycleOwner, { statistics ->
            statisticViewPager.adapter =
                StatisticAdapter(statistics, requireContext())
            indicatorStatistic.setupWithViewPager(statisticViewPager, true)
        })

        viewModel.statisticsCountDays.observe(viewLifecycleOwner, { statistics ->
            countDayViewPager.adapter = CountDaysAdapter(
                statistics,
                requireContext()
            )
            indicatorCountDay.setupWithViewPager(countDayViewPager, true)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}