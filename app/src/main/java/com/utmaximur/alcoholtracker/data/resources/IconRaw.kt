package com.utmaximur.alcoholtracker.data.resources


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.Icon


class IconRaw {

    fun getIconsList(): LiveData<List<Icon>> {
        val iconList: ArrayList<Icon> = ArrayList()

        iconList.add(Icon(R.raw.ic_add_drink_0))
        iconList.add(Icon(R.raw.ic_add_drink_1))
        iconList.add(Icon(R.raw.ic_add_drink_2))
        iconList.add(Icon(R.raw.ic_add_drink_3))
        iconList.add(Icon(R.raw.ic_add_drink_4))
        iconList.add(Icon(R.raw.ic_add_drink_5))
        iconList.add(Icon(R.raw.ic_add_drink_6))
        iconList.add(Icon(R.raw.ic_add_drink_7))
        iconList.add(Icon(R.raw.ic_add_drink_8))
        iconList.add(Icon(R.raw.ic_add_drink_9))
        iconList.add(Icon(R.raw.ic_add_drink_10))
        iconList.add(Icon(R.raw.ic_add_drink_11))
        iconList.add(Icon(R.raw.ic_add_drink_12))
        iconList.add(Icon(R.raw.ic_add_drink_13))
        iconList.add(Icon(R.raw.ic_add_drink_14))

        return MutableLiveData(iconList)
    }
}