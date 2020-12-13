package com.utmaximur.alcoholtracker.data.model

import android.os.Parcel
import android.os.Parcelable

data class AlcoholTrack(

    var id: String,
    val drink: String,
    val volume: String,
    val quantity: Int,
    val degree: String,
    val price: Float,
    val date: Long,
    var icon: Int

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readLong(),
        parcel.readInt()
    )

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<AlcoholTrack> {
        override fun createFromParcel(parcel: Parcel): AlcoholTrack {
            return AlcoholTrack(parcel)
        }

        override fun newArray(size: Int): Array<AlcoholTrack?> {
            return arrayOfNulls(size)
        }
    }
}