package com.utmaximur.alcoholtracker.ui.statistic.presentation.view

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.ui.statistic.presentation.presenter.StatisticPresenter
import com.utmaximur.alcoholtracker.ui.statistic.presentation.presenter.factory.StatisticPresenterFactory
import com.utmaximur.alcoholtracker.ui.statistic.presentation.view.adapter.StatisticViewPagerAdapter
import com.utmaximur.alcoholtracker.ui.statistic.presentation.view.adapter.TopDrinkAdapter

class StatisticFragment :
    StatisticView,
    Fragment() {

    private lateinit var presenter: StatisticPresenter

    private lateinit var topDrinkList: RecyclerView
    private lateinit var statisticPager: ViewPager
    private lateinit var statisticIndicator: WormDotsIndicator

    private lateinit var statisticCountsDays: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_statistic, container, false)
        presenter =
            StatisticPresenterFactory.createPresenter(activity?.applicationContext as Application)
        presenter.onAttachView(this)
        presenter.viewIsReady()
        initUI(view)
        return view
    }

    private fun findViewById(view: View) {
        topDrinkList = view.findViewById(R.id.top_drinks_list)
        statisticPager = view.findViewById(R.id.statistic_view_pager)
        statisticIndicator = view.findViewById(R.id.statistic_indicator)
        statisticCountsDays = view.findViewById(R.id.count_days_text)
    }

    private fun initUI(view: View) {
        findViewById(view)
        topDrinkList.layoutManager = GridLayoutManager(context, 4)
        topDrinkList.adapter = TopDrinkAdapter(presenter.getDrinksDrunkByMe())
        (topDrinkList.adapter as TopDrinkAdapter).setDrinkList(presenter.getAllDrink())

        statisticPager.adapter = StatisticViewPagerAdapter(presenter.getPriceListByPeriod(), requireContext())
        statisticIndicator.setViewPager(statisticPager)

        statisticCountsDays.text = presenter.getCountDayOffYear(requireContext())
    }
}