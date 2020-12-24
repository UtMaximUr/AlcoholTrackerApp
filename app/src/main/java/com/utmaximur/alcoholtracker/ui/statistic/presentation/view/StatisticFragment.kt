package com.utmaximur.alcoholtracker.ui.statistic.presentation.view

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.ui.statistic.presentation.presenter.StatisticPresenter
import com.utmaximur.alcoholtracker.ui.statistic.presentation.presenter.factory.StatisticPresenterFactory
import com.utmaximur.alcoholtracker.ui.statistic.presentation.view.adapter.StatisticViewPagerAdapter
import com.utmaximur.alcoholtracker.ui.statistic.presentation.view.adapter.TopDrinkAdapter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StatisticFragment :
    StatisticView,
    Fragment() {

    private lateinit var presenter: StatisticPresenter

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
        presenter =
            StatisticPresenterFactory.createPresenter(activity?.applicationContext as Application)
        presenter.onAttachView(this)
        Flowable.fromCallable {
            presenter.viewIsReady()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                initUI(view)
            }
            .subscribe()
//        presenter.viewIsReady()
//        initUI(view)
        return view
    }

    private fun findViewById(view: View) {
        topDrinkList = view.findViewById(R.id.top_drinks_list)
        statisticPager = view.findViewById(R.id.statistic_view_pager)
        statisticIndicator = view.findViewById(R.id.view_pager_indicator)
        statisticCountsDays = view.findViewById(R.id.count_days_text)
    }

    @SuppressLint("CheckResult")
    private fun initUI(view: View) {
        findViewById(view)
        topDrinkList.layoutManager = GridLayoutManager(context, 4)
//        topDrinkList.adapter = TopDrinkAdapter(presenter.getDrinksDrunkByMe())
        Flowable.fromCallable {
            presenter.getAllDrink()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list: MutableList<Drink> ->
                    topDrinkList.adapter = TopDrinkAdapter(presenter.getDrinksDrunkByMe())
                    (topDrinkList.adapter as TopDrinkAdapter).setDrinkList(list)
                }
            ) { obj: Throwable ->
                obj.printStackTrace()
                Log.e("fix", "error rx")
            }
//        (topDrinkList.adapter as TopDrinkAdapter).setDrinkList(presenter.getAllDrink())

        Flowable.fromCallable {
            presenter.getPriceListByPeriod()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                statisticIndicator.setupWithViewPager(statisticPager, true)
            }
            .subscribe(
                { list: List<String> ->
                    statisticPager.adapter = StatisticViewPagerAdapter(list, requireContext())
//                    statisticIndicator.setupWithViewPager(statisticPager, true)
                }
            ) { obj: Throwable ->
                obj.printStackTrace()
                Log.e("fix", "error rx")
            }
//        statisticPager.adapter = StatisticViewPagerAdapter(presenter.getPriceListByPeriod(), requireContext())
//        statisticIndicator.setupWithViewPager(statisticPager, true)

        statisticCountsDays.text = presenter.getCountDayOffYear(requireContext())
    }
}