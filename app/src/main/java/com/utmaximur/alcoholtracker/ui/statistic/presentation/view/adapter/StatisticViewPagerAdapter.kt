package com.utmaximur.alcoholtracker.ui.statistic.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.utmaximur.alcoholtracker.R


class StatisticViewPagerAdapter(private var commonPrice: List<String>, private var context: Context) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val period: TextView
        val price: TextView

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = inflater.inflate(
            R.layout.item_statistic, container,
            false
        )
        period = itemView.findViewById(R.id.item_statistic_period)
        price = itemView.findViewById(R.id.item_statistic_price)

        period.text = context.resources.getStringArray(R.array.statistic_period_array)[position]
        price.text = commonPrice[position]

        container.addView(itemView)
        return itemView
    }

    override fun getCount(): Int = commonPrice.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}