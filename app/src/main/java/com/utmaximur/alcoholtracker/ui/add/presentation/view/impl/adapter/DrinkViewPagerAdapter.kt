package com.utmaximur.alcoholtracker.ui.add.presentation.view.impl.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.Drink


class DrinkViewPagerAdapter(private var drinksList: List<Drink>, private var context: Context) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val drinkImage: ImageView
        val drinkName: TextView

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = inflater.inflate(
            R.layout.item_page, container,
            false
        )
        drinkImage = itemView.findViewById(R.id.item_drink_image)
        drinkName = itemView.findViewById(R.id.item_drink_name)

        drinkImage.setImageResource(drinksList[position].image)
        drinkName.text = drinksList[position].drink

        container.addView(itemView)
        return itemView
    }

    override fun getCount(): Int = drinksList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}