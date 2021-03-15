package com.utmaximur.alcoholtracker.ui.add.presentation.view.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.Drink
import java.io.InputStream


class DrinkViewPagerAdapter(private var drinksList: List<Drink>, private var context: Context) :
    PagerAdapter() {


    private var addDrinkListener: AddDrinkListener? = null

    interface AddDrinkListener {
        fun addNewDrink()
        fun deleteDrink(drink: Drink)
        fun editDrink(drink: Drink)
    }

    fun setListener(addDrinkListener: AddDrinkListener) {
        this.addDrinkListener = addDrinkListener
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val drinkImage: ImageView
        val drinkName: TextView
        val drinkDelete: ImageButton
        val drinkEdit: ImageButton
        val drinkAdd: ImageButton

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = inflater.inflate(
            R.layout.item_page, container,
            false
        )
        drinkImage = itemView.findViewById(R.id.item_drink_image)
        drinkName = itemView.findViewById(R.id.item_drink_name)
        drinkDelete = itemView.findViewById(R.id.item_drink_delete)
        drinkEdit = itemView.findViewById(R.id.item_drink_edit)
        drinkAdd = itemView.findViewById(R.id.item_drink_add)

        if (position == drinksList.size) {
            val imageStream: InputStream = itemView.context.resources.openRawResource(R.raw.createdrink)
            val bitmap = BitmapFactory.decodeStream(imageStream)
            drinkImage.setImageBitmap(bitmap)
            drinkAdd.visibility = View.VISIBLE
            drinkName.text = itemView.context.getText(R.string.add_new_drink)
        } else {
            Glide.with(itemView).load(drinksList[position].photo).into(drinkImage)
            drinkName.text = drinksList[position].drink
        }

        if (position < drinksList.size && drinksList[position].id.length > 2) {
            drinkDelete.visibility = View.VISIBLE
            drinkEdit.visibility = View.VISIBLE
        }

        drinkAdd.setOnClickListener {
            addDrinkListener?.addNewDrink()
        }

        drinkDelete.setOnClickListener {
            addDrinkListener?.deleteDrink(drinksList[position])
        }

        drinkEdit.setOnClickListener {
            addDrinkListener?.editDrink(drinksList[position])
        }

        container.addView(itemView)
        return itemView
    }

    override fun getCount(): Int =
        drinksList.size + 1 // добавляем пустой item для добавления своего напитка

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    }
}