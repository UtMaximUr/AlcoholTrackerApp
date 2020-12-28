package com.utmaximur.alcoholtracker.ui.statistic.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.ui.statistic.presentation.view.adapter.StatisticViewPagerAdapter
import com.utmaximur.alcoholtracker.ui.statistic.presentation.view.adapter.TopDrinkAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

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
            Log.e("fix","list -> ${list.size}" )
            topDrinkList.adapter = TopDrinkAdapter(getDrinksDrunkByMe())
            (topDrinkList.adapter as TopDrinkAdapter).setDrinkList(list)
        })

        statisticPager.adapter = StatisticViewPagerAdapter(getPriceListByPeriod(), requireContext())
        statisticIndicator.setupWithViewPager(statisticPager, true)

        statisticCountsDays.text = getCountDayOffYear(requireContext())
    }

    private var weekPeriod = "week"
    private var monthPeriod = "month"
    private var yearPeriod = "year"

    private var allAlcoholTrackList: ArrayList<AlcoholTrack>? = ArrayList()

    private fun initStatistic(){
        viewModel.getAllTrack().observe(viewLifecycleOwner, Observer { list ->
            allAlcoholTrackList?.addAll(list)
            initUI()
        })
    }

    private fun getDrinksDrunkByMe(): Map<String, Int> {
        val drinksDrunkByMe: HashMap<String, Int> = HashMap()
        var count: Int
        allAlcoholTrackList?.forEach {
            if (drinksDrunkByMe.containsKey(it.drink)) {
                count = drinksDrunkByMe[it.drink]!!.toInt()
                count += it.quantity
                drinksDrunkByMe[it.drink] = count
            } else {
                drinksDrunkByMe[it.drink] = it.quantity
            }
        }
        return drinksDrunkByMe
    }

    private fun getCountDayOffYear(context: Context): String {
        val countDays: HashSet<Int> = HashSet()
        val cal = Calendar.getInstance()
        allAlcoholTrackList?.forEach {
            cal.timeInMillis = it.date
            countDays.add(cal.get(Calendar.DAY_OF_MONTH))
        }
        return String.format(
            context.resources.getString(R.string.statistic_count_days),
            context.resources.getQuantityString(
                R.plurals.plurals_day,
                countDays.size,
                countDays.size
            ),
            Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR)
        )
    }

    private fun getPriceListByPeriod(): List<String> {
        val priceList: ArrayList<String> = ArrayList()

        priceList.add(getSumPriceByPeriod(weekPeriod, allAlcoholTrackList!!))
        priceList.add(getSumPriceByPeriod(monthPeriod, allAlcoholTrackList!!))
        priceList.add(getSumPriceByPeriod(yearPeriod, allAlcoholTrackList!!))

        return priceList
    }

    private fun getSumPriceByPeriod(period: String, alcoholTrackList: List<AlcoholTrack>): String {
        val timezone = TimeZone.getDefault()
        val cal = Calendar.getInstance(timezone)
        var sumPrice = 0f
        when (period) {
            weekPeriod -> {
                cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
                cal.set(Calendar.HOUR_OF_DAY, 0)
                alcoholTrackList.forEach {
                    if (it.date > cal.timeInMillis) {
                        sumPrice += it.quantity * it.price
                    }
                }
            }
            monthPeriod -> {
                cal.set(Calendar.DAY_OF_MONTH, 1)
                alcoholTrackList.forEach {
                    if (it.date > cal.timeInMillis) {
                        sumPrice += it.quantity * it.price
                    }
                }
            }
            yearPeriod -> {
                alcoholTrackList.forEach {
                    sumPrice += it.quantity * it.price
                }
            }
        }
        return sumPrice.toString()
    }
}