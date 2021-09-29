package com.utmaximur.alcoholtracker.presantation.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.databinding.FragmentStatisticBinding
import com.utmaximur.alcoholtracker.presantation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.presantation.statistic.adapter.CountDaysAdapter
import com.utmaximur.alcoholtracker.presantation.statistic.adapter.StatisticAdapter
import com.utmaximur.alcoholtracker.presantation.statistic.adapter.TopDrinkAdapter
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

    private fun initUI() {
        viewModel.getStatistics()

        binding.topDrinksList.layoutManager = GridLayoutManager(context, 4)
        val adapter =  TopDrinkAdapter()
        binding.topDrinksList.adapter = adapter

        viewModel.statisticsDrinksList.observe(viewLifecycleOwner, { drinksList ->
            adapter.submitList(drinksList)
        })

        viewModel.statisticsPriceByPeriod.observe(viewLifecycleOwner, { statistics ->
            binding.statisticViewPager.adapter =
                StatisticAdapter(statistics, requireContext())
            binding.indicatorStatistic.setupWithViewPager(binding.statisticViewPager, true)
        })

        viewModel.statisticsCountDays.observe(viewLifecycleOwner, { statistics ->
            binding.countDayViewPager.adapter = CountDaysAdapter(
                statistics,
                requireContext()
            )
            binding.indicatorCountDay.setupWithViewPager(binding.countDayViewPager, true)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}