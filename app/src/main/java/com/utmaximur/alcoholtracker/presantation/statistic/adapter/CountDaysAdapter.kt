package com.utmaximur.alcoholtracker.presantation.statistic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.utmaximur.alcoholtracker.R
import java.util.*


class CountDaysAdapter(private var statisticCountDays: List<Int>, private var context: Context) :
    PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val statistic: TextView

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = inflater.inflate(
            R.layout.item_count_days, container,
            false
        )
        statistic = itemView.findViewById(R.id.item_count_days_text)
        if (position == 0) {
            statistic.text = String.format(
                context.resources.getString(R.string.statistic_count_days),
                context.resources.getQuantityString(
                    R.plurals.plurals_day,
                    statisticCountDays.first(),
                    statisticCountDays.first()
                ),
                Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR)
            )
        } else {
            statistic.text = String.format(
                context.resources.getString(R.string.statistic_count_days_no_drink),
                context.resources.getQuantityString(
                    R.plurals.plurals_day,
                    statisticCountDays.last(),
                    statisticCountDays.last()
                )
            )
        }

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