package com.utmaximur.alcoholtracker.presantation.statistic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.utmaximur.alcoholtracker.R


class CountDaysAdapter(private var statisticCountDays: List<String>, private var context: Context) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val statistic: TextView

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = inflater.inflate(
            R.layout.item_count_days, container,
            false
        )
        statistic = itemView.findViewById(R.id.item_count_days_text)
        statistic.text = statisticCountDays[position]

        container.addView(itemView)
        return itemView
    }

    override fun getCount(): Int = statisticCountDays.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}