package com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.presenter.impl

import android.content.Context
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.ui.add.addtrack.interactor.AddInteractor
import com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.presenter.AddPresenter
import com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.view.AddView
import com.utmaximur.alcoholtracker.ui.base.BasePresenter
import java.text.SimpleDateFormat
import java.util.*


class AddPresenterImpl(private val interactor: AddInteractor) :
    BasePresenter<AddView>(),
    AddPresenter {

    override fun viewIsReady() {
        // do nothing
    }

    override fun onSaveButtonClick() {

        if (view != null) {

            val id: String = view!!.getIdDrink()

            val drink: String = view!!.getDrink()

            val volume: String = view!!.getVolume()

            val quantity: Int = view!!.getQuantity()

            val degree: String = view!!.getDegree()

            val price: Float = view!!.getPrice()

            val date: Long = view!!.getDate()

            val icon: Int = view!!.getIcon()

            interactor.save(id, drink, volume, quantity, degree, price, date, icon)
        }
    }

    override fun getAllDrink(): MutableList<Drink>{
        return interactor.getAllDrink()
    }

    override fun getFloatDegree(): Array<String?> {
        val nums: Array<String?> = arrayOfNulls(200)
        var double = 0.0
        for (i in 0..199) {
            double += 0.5
            val format: String = String.format("%.1f", double)
            nums[i] = format
        }
        return nums
    }

    override fun checkIsEmptyField(): Boolean {
        return if(view!!.getPrice() == 0.0f || view!!.getDate() == 0L){
            view!!.showWarningEmptyField()
            false
        }else{
            true
        }
    }

    override fun checkIsEmptyFieldPrice(): Boolean {
        return view!!.getPrice() != 0.0f
    }

    override fun getTotalLitres(pos: Int): String? {
        return if(view!!.getVolumeList()[pos].toDouble() < 2){
            String.format("%.2f", view!!.getVolumeList()[pos].toDouble() * view!!.getQuantity())
        }else{
            String.format("%.2f", view!!.getVolumeList()[pos].toDouble() * view!!.getQuantity() / 1000)
        }
    }

    override fun getTotalMoney(): String? {
        return (view!!.getQuantity() * view!!.getPrice().toString().toDouble()).toString()
    }

    override fun setDateOnButton(context: Context, date: Date): String {
        val sdf = SimpleDateFormat(context.resources.getString(R.string.date_format_pattern), Locale.getDefault())
        return String.format("%s", sdf.format(date))
    }
}