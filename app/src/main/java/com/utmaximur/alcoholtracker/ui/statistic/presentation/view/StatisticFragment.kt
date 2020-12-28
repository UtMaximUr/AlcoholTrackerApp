package com.utmaximur.alcoholtracker.ui.statistic.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.dagger.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.dagger.factory.StatisticViewModelFactory
import com.utmaximur.alcoholtracker.ui.statistic.presentation.view.adapter.StatisticViewPagerAdapter
import com.utmaximur.alcoholtracker.ui.statistic.presentation.view.adapter.TopDrinkAdapter

class StatisticFragment :
    StatisticView,
    Fragment() {

    private lateinit var viewModel: StatisticViewModel

    private lateinit var topDrinkList: RecyclerView
    private lateinit var statisticPager: ViewPager
    private lateinit var statisticIndicator: TabLayout

    private lateinit var statisticCountsDays: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_statistic, container, false)
        injectDagger()
        initViewModel()
        findViewById(view)
        initStatistic()
        return view
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

    private fun findViewById(view: View) {
        topDrinkList = view.findViewById(R.id.top_drinks_list)
        statisticPager = view.findViewById(R.id.statistic_view_pager)
        statisticIndicator = view.findViewById(R.id.view_pager_indicator)
        statisticCountsDays = view.findViewById(R.id.count_days_text)
    }

    private fun initUI() {
        topDrinkList.layoutManager = GridLayoutManager(context, 4)
        viewModel.getAllDrink().observe(viewLifecycleOwner, Observer { list ->
            topDrinkList.adapter = TopDrinkAdapter(viewModel.getDrinksDrunkByMe())
            (topDrinkList.adapter as TopDrinkAdapter).setDrinkList(list)
        })

        statisticPager.adapter = StatisticViewPagerAdapter(viewModel.getPriceListByPeriod(), requireContext())
        statisticIndicator.setupWithViewPager(statisticPager, true)

        statisticCountsDays.text = viewModel.getCountDayOffYear(requireContext())
    }

    private fun initStatistic(){
        viewModel.getAllTrack().observe(viewLifecycleOwner, Observer { list ->
            viewModel.loadTrackList(list)
            initUI()
        })
    }
}