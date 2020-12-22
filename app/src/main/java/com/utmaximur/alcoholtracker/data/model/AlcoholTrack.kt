package com.utmaximur.alcoholtracker.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_database")
data class AlcoholTrack(

    @PrimaryKey
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
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeStringArray(
            arrayOf(
                id, drink, volume, quantity.toString(), degree, price.toString(),
                date.toString(),
                icon.toString()
            )
        )
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