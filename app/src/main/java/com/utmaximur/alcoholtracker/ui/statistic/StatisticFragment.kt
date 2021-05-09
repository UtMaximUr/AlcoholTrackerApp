package com.utmaximur.alcoholtracker.ui.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.databinding.FragmentStatisticBinding
import com.utmaximur.alcoholtracker.di.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.di.factory.StatisticViewModelFactory
import com.utmaximur.alcoholtracker.ui.statistic.adapter.StatisticViewPagerAdapter
import com.utmaximur.alcoholtracker.ui.statistic.adapter.TopDrinkAdapter
import com.utmaximur.alcoholtracker.util.alphaView

class StatisticFragment :
    Fragment() {

    private lateinit var viewModel: StatisticViewModel

    private lateinit var binding: FragmentStatisticBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDagger()
        initViewModel()
        binding = FragmentStatisticBinding.inflate(layoutInflater)
        initStatistic()
        return binding.root
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initViewModel() {
        val dependencyFactory: AlcoholTrackComponent =
            (requireActivity().application as App).alcoholTrackComponent
        val drinkRepository = dependencyFactory.provideDrinkRepository()
        val trackRepository = dependencyFactory.provideTrackRepository()
        val viewModel: StatisticViewModel by viewModels {
            StatisticViewModelFactory(drinkRepository, trackRepository)
        }
        this.viewModel = viewModel
    }

    private fun initUI() {
        binding.topDrinksList.alphaView()
        binding.topDrinksList.layoutManager = GridLayoutManager(context, 4)
        viewModel.getAllDrink().observe(viewLifecycleOwner, { list ->
            binding.topDrinksList.adapter = TopDrinkAdapter(list, viewModel.getDrinksDrunkByMe())
        })

        binding.statisticViewPager.adapter = StatisticViewPagerAdapter(viewModel.getPriceListByPeriod(), requireContext())
        binding.viewPagerIndicator.setupWithViewPager(binding.statisticViewPager, true)

        binding.countDaysText.text = viewModel.getCountDayOffYear(requireContext())
    }

    private fun initStatistic(){
        viewModel.getAllTrack().observe(viewLifecycleOwner, { list ->
            viewModel.loadTrackList(list)
            initUI()
        })
    }
}